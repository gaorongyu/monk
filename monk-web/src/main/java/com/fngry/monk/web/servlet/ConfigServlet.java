package com.fngry.monk.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fngry.monk.biz.service.config.ConfigServerInner;
import com.fngry.monk.common.util.StringUtil;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Created by gaorongyu on 2017/11/11.
 */
@WebServlet(urlPatterns = "/config", asyncSupported = true)
public class ConfigServlet extends HttpServlet {

    private static final String PARAM_PROBE_MODIFY = "probeModify";

    private ConfigServerInner configServerInner;

    @Override
    public void init() {
        WebApplicationContext wac = WebApplicationContextUtils.getWebApplicationContext(getServletContext());
        configServerInner = (ConfigServerInner) wac.getBean("configServerInner");
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String probeModify= req.getParameter(PARAM_PROBE_MODIFY);

        if (StringUtil.isBlank(probeModify)) {
            throw new IOException(" probeModify param error! ");
        }

        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);
        configServerInner.doPollingConfig(req, resp, probeModify);
    }

}
