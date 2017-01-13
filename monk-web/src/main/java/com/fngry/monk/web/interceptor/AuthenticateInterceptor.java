package com.fngry.monk.web.interceptor;

import com.fngry.monk.web.handler.IRequestHandler;
import lombok.Setter;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by gaorongyu on 16/11/13.
 */
public class AuthenticateInterceptor extends HandlerInterceptorAdapter {

    @Setter
    private List<IRequestHandler> authenticateHandlers = null;

    /**
     * This implementation always returns {@code true}.
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        authenticate(request, response);
        return super.preHandle(request, response, handler);
    }

    /**
     * 遍历认证器做认证处理
     *
     * @param request
     * @param response
     */
    private void authenticate(HttpServletRequest request, HttpServletResponse response) {
        if (this.authenticateHandlers != null) {
            for (IRequestHandler handler : this.authenticateHandlers) {
                handler.handle(request, response);
            }
        }
    }

}
