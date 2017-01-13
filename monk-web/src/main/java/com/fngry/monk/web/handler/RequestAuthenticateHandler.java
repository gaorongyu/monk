package com.fngry.monk.web.handler;

import com.fngry.monk.common.util.HttpUtil;
import com.fngry.monk.common.util.StringUtil;
import lombok.Setter;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

//import org.springframework.http.HttpEntity;

/**
 *
 * 外部系统接入统一认证
 *
 * Created by gaorongyu on 16/11/30.
 */
@Component
public class RequestAuthenticateHandler implements IRequestHandler {

    @Setter
    private Map<String, String> sidAndPassword = null;

    /**
     *
     * 外部请求认证处理
     *
     * @param request
     * @param response
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response) {

        String requestIP = HttpUtil.getRequestIp(request);

        String sid = request.getParameter("sid");
        String sPassword = request.getParameter("sPassword");

        if (StringUtil.isBlank(sid)) {
            // entity 参数
//            HttpEntity entity = ((HttpEntityEnclosingRequest) request).getEntity();
        }

    }

}
