package com.fngry.monk.biz.service.config;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 2017/11/11.
 */
@Component("configServerInner")
public class ConfigServerInner {

    @Resource
    private LongPullingService longPullingService;

    public void doPollingConfig(HttpServletRequest req, HttpServletResponse resp, String probeModify) {
        // 长轮询
        if (longPullingService.isSupportLongPulling(req)) {
            Map<String, String> clientMd5Map = ConfigControl.getClientMd5Map(probeModify);
            longPullingService.addLongPullingClient(req, resp, clientMd5Map);
        }
        // todo 支持短轮询
    }

}
