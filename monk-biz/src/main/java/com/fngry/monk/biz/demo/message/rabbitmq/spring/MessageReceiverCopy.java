package com.fngry.monk.biz.demo.message.rabbitmq.spring;

import com.fngry.monk.biz.constant.BizConstants;
import com.fngry.monk.biz.demo.message.rabbitmq.MessageHandlerCopy;
import com.fngry.monk.biz.demo.message.rabbitmq.RabbitMqConfig;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Created by gaorongyu on 16/11/18.
 */
@Component
public class MessageReceiverCopy {

    @Value("${monk.rabbit.mq.queue}")
    private String queue;

    @Autowired
    private RabbitMqConfig rabbitMqConfig;

    @Autowired
    private MessageHandlerCopy messageHandler;

    public void init() {

        CachingConnectionFactory cf = ConnectionFactory.getCachingConnectionFactory(rabbitMqConfig);

        MessageListener listener = new MessageListenerAdapter(messageHandler);
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(listener);
        container.setConnectionFactory(cf);
        // 设置监听的消息队列  接收这些队列的消息
        container.setQueueNames(queue);
        container.setConcurrentConsumers(BizConstants.CONCURRENT_CONSUMERS);
        container.setMaxConcurrentConsumers(BizConstants.MAX_CONCURRENT_CONSUMERS);
        container.setPrefetchCount(BizConstants.PREFETCH_COUNT);
        container.start();
    }

}
