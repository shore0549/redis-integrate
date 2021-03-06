package com.git.hui.rabbit.spring.consumer;

//import com.git.hui.rabbit.spring.factory.AmqpConsumerFactory;

import com.git.hui.rabbit.spring.factory.MessageListenerContainerFactory;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;

/**
 * Created by yihui in 11:35 18/5/30.
 */
@Slf4j
//@Component
public class AmqpConsumer {
    private volatile boolean end = false;

    public AmqpConsumer() {
        System.out.println("AmqpConsumer init! ");
    }

    public ChannelAwareMessageListener buildMessageListener(MessageListenerContainer container) {
        return new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                try {
                    byte[] bytes = message.getBody();
                    String data = new String(bytes, "utf-8");
                    System.out.println("AmqpConsumer : data = " + data);
                } catch (Exception e) {
                    log.error("local cache rabbit mq localQueue error! e: {}", e);
                } finally {
                    channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
                    if (end) {
                        container.stop();
                    }
                }
            }
        };
    }

    @Bean(name = "amqpConsumerContainer")
    public MessageListenerContainer container(MessageListenerContainerFactory dirMsgContainerFac) throws Exception {
        SimpleMessageListenerContainer container = dirMsgContainerFac.getObject();
        container.setMessageListener(buildMessageListener(container));
        return container;
    }


    @Bean(name = "dirMsgContainerFac")
    public MessageListenerContainerFactory messageListenerContainerFactory(ConnectionFactory connectionFactory,
            RabbitAdmin rabbitAdmin) {
        return MessageListenerContainerFactory.builder().autoAck(true).autoDeleted(false).durable(true)
                .exchange("direct.exchange").routingKey("test1").queue("hello").connectionFactory(connectionFactory)
                .rabbitAdmin(rabbitAdmin).build();
    }

}
