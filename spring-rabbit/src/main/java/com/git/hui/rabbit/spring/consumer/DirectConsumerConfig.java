package com.git.hui.rabbit.spring.consumer;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yihui in 15:05 18/5/30.
 */
@Configuration
public class DirectConsumerConfig {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Bean
    public DirectExchange directExchange() {
        DirectExchange directExchange = new DirectExchange("direct.exchange");
        directExchange.setAdminsThatShouldDeclare(rabbitAdmin);
        return directExchange;
    }

    @Bean
    public Queue directQueue() {
        Queue queue = new Queue("aaa");
        queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    @Bean
    public Binding directQueueBinding() {
        Binding binding = BindingBuilder.bind(directQueue()).to(directExchange()).with("test1");
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public ChannelAwareMessageListener directConsumer() {
        return new BasicConsumer("direct");
    }

    @Bean(name = "directMessageListenerContainer")
    public MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setRabbitAdmin(rabbitAdmin);
        container.setQueues(directQueue());
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(directConsumer());
        return container;
    }

}
