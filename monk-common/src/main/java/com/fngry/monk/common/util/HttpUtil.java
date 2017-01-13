package com.fngry.monk.common.util;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by gaorongyu on 16/11/30.
 */
public class HttpUtil {

    /**
     * 获取访问的IP 有代理的情况返回原始请求方的IP
     *
     * @param request 请求信息
     * @return IP
     */
    public static String getRequestIp(HttpServletRequest request) {
        if (request == null) {
            return null;
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}
