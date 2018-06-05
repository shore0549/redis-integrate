package com.git.hui.rabbit.spring.component;

import com.git.hui.rabbit.spring.component.consumer.AbsMQConsumer;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * Created by yihui in 18:51 18/6/4.
 */
public class DynamicConsumer {
    public DynamicConsumer(MQContainerFactory fac) throws Exception {
        SimpleMessageListenerContainer container = fac.getObject();
        container.setMessageListener(new AbsMQConsumer() {
            @Override
            public boolean process(Message message, Channel channel) {
                System.out.println("DynamicConsumer: " + fac.getQueue() + " | " + new String(message.getBody()));
                return true;
            }
        });

        container.start();
    }
}
