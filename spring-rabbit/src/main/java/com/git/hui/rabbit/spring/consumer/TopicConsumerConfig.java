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
 * Created by yihui in 15:22 18/5/30.
 */
@Configuration
public class TopicConsumerConfig {
    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Bean
    public TopicExchange topicExchange() {
        TopicExchange topicExchange = new TopicExchange("topic.exchange");
        topicExchange.setAdminsThatShouldDeclare(rabbitAdmin);
        return topicExchange;
    }

    @Bean
    public Queue topicQueue() {
        Queue queue = new Queue("bbb");
        queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    @Bean
    public Binding topicQueueBinding() {
        Binding binding = BindingBuilder.bind(topicQueue()).to(topicExchange()).with("*.queue");
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public ChannelAwareMessageListener topicConsumer() {
        return new BasicConsumer("topic");
    }

    @Bean(name = "topicMessageListenerContainer")
    public MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setRabbitAdmin(rabbitAdmin);
        container.setQueues(topicQueue());
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(topicConsumer());
        return container;
    }
}
