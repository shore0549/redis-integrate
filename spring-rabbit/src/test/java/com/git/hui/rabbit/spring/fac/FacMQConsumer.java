package com.git.hui.rabbit.spring.fac;

import com.git.hui.rabbit.spring.component.consumer.AbsMQConsumer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;

/**
 * Created by yihui in 16:17 18/6/1.
 */
public class FacMQConsumer extends AbsMQConsumer {
    private String name;
    public FacMQConsumer(String name) {
        this.name = name;
    }

    @Override
    public boolean process(Message message, Channel channel) {
        String data = new String(message.getBody());
        System.out.println(name + " fac mq consumer: " + data + " " + Thread.currentThread().getName());
        return false;
    }
}
