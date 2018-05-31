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
 * Created by yihui in 15:42 18/5/30.
 */
@Configuration
public class FanoutConsumerConfig {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Bean
    public FanoutExchange fanoutExchange() {
        FanoutExchange fanoutExchange = new FanoutExchange("fanout.exchange");
        fanoutExchange.setAdminsThatShouldDeclare(rabbitAdmin);
        return fanoutExchange;
    }

    @Bean
    public Queue fanoutQueue() {
        Queue queue = new Queue("ccc");
        queue.setAdminsThatShouldDeclare(rabbitAdmin);
        return queue;
    }

    @Bean
    public Binding fanoutQueueBinding() {
        Binding binding = BindingBuilder.bind(fanoutQueue()).to(fanoutExchange());
        binding.setAdminsThatShouldDeclare(rabbitAdmin);
        return binding;
    }

    @Bean
    public ChannelAwareMessageListener fanoutConsumer() {
        return new BasicConsumer("fanout");
    }

    @Bean(name = "FanoutMessageListenerContainer")
    public MessageListenerContainer messageListenerContainer() {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setRabbitAdmin(rabbitAdmin);
        container.setQueues(fanoutQueue());
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);
        container.setMessageListener(fanoutConsumer());
        return container;
    }


}
