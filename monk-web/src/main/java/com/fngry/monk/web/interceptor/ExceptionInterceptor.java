package com.fngry.monk.web.interceptor;

import com.fngry.monk.web.util.RequestExceptionUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaorongyu on 16/11/13.
 */
//@ControllerAdvice
public class ExceptionInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void afterCompletion(
            HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {


        super.afterCompletion(request, response, handler, ex);

        // 异常信息统一处理 业务异常封装成提示信息
        if (ex != null) {
            RequestExceptionUtil.handleException(ex, request, response);
        }
    }

    @ExceptionHandler
    public void proc(RuntimeException e) {

    }

}
