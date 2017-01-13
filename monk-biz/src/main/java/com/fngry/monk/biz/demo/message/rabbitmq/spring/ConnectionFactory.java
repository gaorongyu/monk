package com.fngry.monk.biz.demo.message.rabbitmq.spring;

import com.fngry.monk.biz.demo.message.rabbitmq.RabbitMqConfig;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;

/**
 * Created by gaorongyu on 16/12/7.
 */
public class ConnectionFactory {

    private static CachingConnectionFactory cf = null;

    public static synchronized  CachingConnectionFactory getCachingConnectionFactory(RabbitMqConfig config) {
        if (cf != null) {
            return cf;
        }
        cf = new CachingConnectionFactory();
        cf.setHost(config.getHost());
        cf.setPort(config.getPort());
        cf.setVirtualHost(config.getVirtualHost());
        cf.setUsername(config.getUserName());
        cf.setPassword(config.getPassWord());

        return cf;
    }

}
