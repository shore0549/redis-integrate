package com.git.hui.rabbit.spring.component.consumer;

import com.git.hui.rabbit.spring.component.MQContainerFactory;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * Created by yihui in 18:51 18/6/4.
 */
public class DynamicConsumer {
    private SimpleMessageListenerContainer container;

    public DynamicConsumer(MQContainerFactory fac) throws Exception {
        SimpleMessageListenerContainer container = fac.getObject();
        container.setMessageListener(new AbsMQConsumer() {
            @Override
            public boolean process(Message message, Channel channel) {
                System.out.println("DynamicConsumer: " + fac.getQueue() + " | " + new String(message.getBody()));
                return true;
            }
        });
    }

    public void start() {
        container.start();
    }

    public void stop() {
        container.stop();
    }

    public void shutdown() {
        container.shutdown();
    }
}
