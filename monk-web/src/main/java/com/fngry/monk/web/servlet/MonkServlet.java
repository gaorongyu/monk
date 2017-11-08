package com.fngry.monk.web.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by gaorongyu on 2017/11/8.
 */
@WebServlet(urlPatterns = "/monk_servlet", asyncSupported = true)
public class MonkServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=utf-8");
        PrintWriter out = resp.getWriter();
        out.println(" Servlet doGet start time: " + new Date());
        out.flush();

        // 设置属性支持异步处理 设置request的属性org.apache.catalina.ASYNC_SUPPORTED
        // 或者把web.xml定义的所有filter设置async-supported=true
        req.setAttribute("org.apache.catalina.ASYNC_SUPPORTED", true);

        // 在子线程处理业务, 由子线程负责输出响应, 主线程退出
        AsyncContext asyncContext = req.startAsync(req, resp);
        asyncContext.start(new BizExecutor(asyncContext));

        out.println(" Servlet doGet end time: " + new Date());
        out.flush();
    }

}