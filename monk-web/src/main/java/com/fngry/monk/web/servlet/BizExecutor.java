package com.fngry.monk.web.servlet;

import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.AsyncContext;

/**
 *
 * Servlet 异步处理
 *
 * Created by gaorongyu on 2017/11/8.
 */
public class BizExecutor implements Runnable {

    private AsyncContext asyncContext;

    BizExecutor(AsyncContext asyncContext) {
        this.asyncContext = asyncContext;
    }

    @Override
    public void run() {
        try {
            // 模拟业务操作
            Thread.sleep(5000);
            System.out.println(" do biz end ");

            PrintWriter out = asyncContext.getResponse().getWriter();
            out.println("biz dispose end time: " + new Date());
            out.flush();
            asyncContext.complete();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
