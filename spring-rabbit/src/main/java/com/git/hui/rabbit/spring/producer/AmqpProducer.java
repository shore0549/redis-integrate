package com.git.hui.rabbit.spring.producer;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * Created by yihui in 11:16 18/5/30.
 */
@Component
public class AmqpProducer {

    private AmqpTemplate amqpTemplate;

    @Autowired
    public void amqpTemplate(ConnectionFactory connectionFactory) {
        amqpTemplate = new RabbitTemplate(connectionFactory);
    }

    /**
     * 将消息发送到指定的交换器上
     *
     * @param exchange
     * @param msg
     */
    public void publishMsg(String exchange, String routingKey, Object msg) {
        amqpTemplate.convertAndSend(exchange, routingKey, msg);
    }
}
