package com.fngry.monk.biz.demo.message.rabbitmq.spring;

import com.fngry.monk.biz.demo.message.rabbitmq.RabbitMqConfig;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 16/12/7.
 */
@Component
public class MessageSender {

    @Value("${monk.rabbit.mq.rootingKey}")
    private String rootingKey;

    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    private RabbitTemplate rabbitTemplate;

    // 先调用构造方法  再给属性注入 placeholder 的值

    public void init() {
        CachingConnectionFactory cf = ConnectionFactory.getCachingConnectionFactory(rabbitMqConfig);
        rabbitTemplate = new RabbitTemplate(cf);
    }


    public void send(String message) {
        rabbitTemplate.convertAndSend(rabbitMqConfig.getExchange(), rootingKey, message);
    }


}
