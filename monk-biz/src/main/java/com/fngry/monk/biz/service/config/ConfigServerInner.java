package com.fngry.monk.biz.service.config;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 2017/11/11.
 */
@Component("configServerInner")
public class ConfigServerInner {

    private static final Logger logger = LoggerFactory.getLogger(ConfigServerInner.class);

    @Resource
    private LongPullingService longPullingService;

    public void doPollingConfig(HttpServletRequest req, HttpServletResponse resp, String probeModify) {
        Map<String, String> clientMd5Map = ConfigControl.getClientMd5Map(probeModify);
        // 根据参数 进行长轮询或即时查询
        if (longPullingService.isSupportLongPulling(req)) {
            longPullingService.addLongPullingClient(req, resp, clientMd5Map);
        } else {
            queryConfigChange(req, resp, clientMd5Map);
        }
    }

    private void queryConfigChange(HttpServletRequest req, HttpServletResponse resp, Map<String, String> clientMd5Map) {
        List<String> changedGroups = ConfigControl.compareMd5(req, resp, clientMd5Map);
        if (changedGroups.size() > 0) {
            try {
                PrintWriter out = resp.getWriter();

                String groupKeys = ConfigControl.getGroupKeys(changedGroups);
                out.println("groupKeys:" + groupKeys + ";changeTime:" + System.currentTimeMillis());
                out.flush();
            } catch (IOException e) {
                logger.error("doSendResponse error!", e);
            }
        }
    }

}
