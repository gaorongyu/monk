package com.fngry.monk.biz.service.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.servlet.AsyncContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fngry.monk.biz.service.config.event.DataChangeEvent;
import com.fngry.monk.biz.service.config.event.Event;
import com.fngry.monk.biz.service.config.listener.AbstractEventListener;
import com.fngry.monk.common.util.StringUtil;
import org.apache.commons.lang3.concurrent.BasicThreadFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 *
 * Long Pulling Service
 *
 * Created by gaorongyu on 2017/11/11.
 */
@Component("longPullingService")
public class LongPullingService extends AbstractEventListener {

    private static final Logger logger = LoggerFactory.getLogger(LongPullingService.class);

    private static final String LONG_PULLING_HEADER = "longPullingTimeout";

    private static final long DEFAULT_TIMEOUT = 30000L;

    private static final int DEFAULT_POOL_SIZE = 1;

    private ScheduledExecutorService scheduledExecutorService = null;

    private List<ClientLongPulling> pullingSubs = new ArrayList<>();

    @PostConstruct
    public void init() {
        scheduledExecutorService = new ScheduledThreadPoolExecutor(DEFAULT_POOL_SIZE,
                new BasicThreadFactory.Builder().namingPattern("config-schedule-pool-%d").daemon(false).build());
    }

    @Override
    public List<Class<? extends Event>> interest() {
        List<Class<? extends Event>> list = new ArrayList<>();
        list.add(DataChangeEvent.class);
        return list;
    }

    @Override
    public void onEvent(Event event) {
        if (event instanceof DataChangeEvent) {
            DataChangeEvent evt = (DataChangeEvent) event;
            scheduledExecutorService.execute(new DataChangeTask(evt.getGroupKey(), evt.getChangeTime()));
        }
    }

    public boolean isSupportLongPulling(HttpServletRequest req) {
        return req.getParameter(LONG_PULLING_HEADER) != null;
    }

    public void addLongPullingClient(HttpServletRequest req, HttpServletResponse resp, Map<String, String> clientMd5Map) {
        // 在子线程处理业务, 由子线程负责输出响应, 主线程退出
        final AsyncContext asyncContext = req.startAsync();
        asyncContext.setTimeout(0L);

        scheduledExecutorService.execute(new ClientLongPulling(asyncContext, clientMd5Map));
    }

    private long getLongPullingTimeout(ServletRequest req) {
        String clientTimeout = req.getParameter(LONG_PULLING_HEADER);

        long timeout = DEFAULT_TIMEOUT;
        if (StringUtil.isBlank(clientTimeout)) {
            return timeout;
        }
        try {
            timeout = Long.parseLong(clientTimeout);
        } catch (NumberFormatException e) {
            logger.error("parse longPullingTimeout error!", e);
        }
        return timeout;
    }

    /**
     * 长轮询线程
     *  数据有更新则通过onEvent() -> DataChangeTask.run() 返回结果
     *  数据没有更新则等待getLongPullingTimeout()时间后返回结果
     */
    class ClientLongPulling implements Runnable {

        private AsyncContext asyncContext;

        private Future future;

        private Map<String, String> clientMd5Map;

        ClientLongPulling(AsyncContext asyncContext, Map<String, String> clientMd5Map) {
            this.asyncContext = asyncContext;
            this.clientMd5Map = clientMd5Map;
        }

        @Override
        public void run() {
            HttpServletRequest req = (HttpServletRequest) asyncContext.getRequest();
            HttpServletResponse resp = (HttpServletResponse) asyncContext.getResponse();
            List<String> changedGroups = ConfigControl.compareMd5(req, resp, clientMd5Map);

            // date changed then send response
            if (changedGroups.size() > 0) {
                sendResponse(changedGroups, System.currentTimeMillis());
                return;
            }

            // send response to client if no data change until timeout
            future = scheduledExecutorService.schedule(new Runnable() {
                @Override
                public void run() {
                    pullingSubs.remove(ClientLongPulling.this);
                    sendResponse(null, 0);
                }
            }, getLongPullingTimeout(req), TimeUnit.MILLISECONDS);

            pullingSubs.add(this);
        }

        private void sendResponse(List<String> changedGroups, long changeTime) {
            if (future != null) {
                future.cancel(false);
            }
            // send response to client
            doSendResponse(changedGroups, changeTime);
            // complete async
            asyncContext.complete();
        }

        private void doSendResponse(List<String> changedGroups, long changeTime) {
            try {
                PrintWriter out = asyncContext.getResponse().getWriter();

                StringBuffer groupKeys = new StringBuffer();
                for (int i = 0; i < changedGroups.size(); i++) {
                    String groupKey = changedGroups.get(i);
                    groupKeys.append(groupKey);

                    if (i < changedGroups.size() - 1) {
                        groupKeys.append(ConfigControl.SEPARATOR_GROUP_KEY);
                    }
                }
                out.println("groupKeys:" + groupKeys + ";changeTime:" + changeTime);
                out.flush();
            } catch (IOException e) {
                logger.error("doSendResponse error!", e);
            }
        }
    }

    /**
     * 数据变更任务 数据有修改时通过数据变更事件启动该线程
     */
    class DataChangeTask implements Runnable {

        private String groupKey;

        private long changeTime;

        DataChangeTask(String groupKey, long changeTime) {
            this.groupKey = groupKey;
            this.changeTime = changeTime;
        }

        @Override
        public void run() {
            for (Iterator<ClientLongPulling> iter = pullingSubs.iterator(); iter.hasNext();) {
                ClientLongPulling clientLongSub = iter.next();

                if (clientLongSub.clientMd5Map.containsKey(groupKey)) {
                    clientLongSub.sendResponse(Arrays.asList(groupKey), changeTime);
                    iter.remove();
                }
            }
        }

    }

}
