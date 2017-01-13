package com.fngry.monk.biz.demo.message.rabbitmq;

import org.springframework.stereotype.Component;

/**
 *
 * mq需奥西处理器
 *
 * Created by gaorongyu on 16/11/18.
 */
@Component
public class MessageHandlerCopy {

    public void handleMessage(Object message) {
        System.out.println("receive message is: " + message);

        // 如果抛异常 队列里消息不会删除 会自动重发
        throw new RuntimeException("test");
    }

}
