package com.git.hui.rabbit.spring.consumer;

//import com.git.hui.rabbit.spring.factory.AmqpConsumerFactory;

import com.git.hui.rabbit.spring.factory.AmqpConsumerFactory;
import com.git.hui.rabbit.spring.helper.AmqpHelper;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.context.annotation.Bean;

/**
 * Created by yihui in 11:35 18/5/30.
 */
@Slf4j
//@Component
public class AmqpConsumer {
    private volatile boolean end = false;

    public ChannelAwareMessageListener buildMessageListener(MessageListenerContainer container) {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    byte[] bytes = message.getBody();
                    String data = new String(bytes, "utf-8");
                    System.out.println("data" + data);
                } catch (Exception e) {
                    log.error("local cache rabbit mq localQueue error! e: {}", e);
                } finally {
                    if (end) {
                        container.stop();
                    }
                }
            }
        };
    }

    @Bean
    public AmqpHelper amqpHelper(AmqpConsumerFactory amqpConsumerFactory) {
        AmqpHelper amqpHelper = amqpConsumerFactory.getObject();
        amqpHelper.getContainer().setMessageListener(buildMessageListener(amqpHelper.getContainer()));
        System.out.println("amqp helper init!!!");
        return amqpHelper;
    }

    @Bean
    public AmqpConsumerFactory amqpConsumerFactory(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin) {
        return AmqpConsumerFactory.builder().autoAck(true).autoDeleted(false).durable(true).exchange("direct.exchange")
                .queue("hello").connectionFactory(connectionFactory).rabbitAdmin(rabbitAdmin).build();
    }
}
