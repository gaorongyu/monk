package com.fngry.monk.web.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaorongyu on 16/11/30.
 */
public interface IRequestHandler {

    /**
     *
     * http请求 controller调用前、后处理接口
     *
     * @param request
     * @param response
     */
    public void handle(HttpServletRequest request, HttpServletResponse response);

}
