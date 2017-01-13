package com.fngry.monk.biz.demo.message.rabbitmq;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 连接rabbitMq的通用参数
 * Created by gaorongyu on 16/11/21.
 */
@Component
@Data
public class RabbitMqConfig {

    @Value("${monk.rabbit.mq.host}")
    private String host;

    @Value("${monk.rabbit.mq.port}")
    private Integer port;

    @Value("${monk.rabbit.mq.virtualHost}")
    private String virtualHost;

    @Value("${monk.rabbit.mq.exchange}")
    private String exchange;

    @Value("${monk.rabbit.mq.userName}")
    private String userName;

    @Value("${monk.rabbit.mq.password}")
    private String passWord;

}
