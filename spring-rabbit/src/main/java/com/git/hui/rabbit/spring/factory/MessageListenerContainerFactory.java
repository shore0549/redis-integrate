package com.git.hui.rabbit.spring.factory;

import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.FactoryBean;

/**
 * Created by yihui in 20:14 18/5/31.
 */
@Data
@Builder
public class MessageListenerContainerFactory implements FactoryBean<SimpleMessageListenerContainer> {

    private String exchange;
    private String queue;
    private String routingKey;
    private boolean autoDeleted;
    private boolean durable;
    private boolean autoAck;
    private ConnectionFactory connectionFactory;
    private RabbitAdmin rabbitAdmin;


    private Queue buildQueue() {
        return new Queue(queue, durable, false, autoDeleted);
    }

    private DirectExchange buildTopicExchange() {
        return new DirectExchange(exchange);
    }

    private Binding buildBinding(Queue queue, DirectExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }


    @Override
    public SimpleMessageListenerContainer getObject() throws Exception {
        Queue q = buildQueue();
        DirectExchange exchange = buildTopicExchange();
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareQueue(q);
        rabbitAdmin.declareBinding(buildBinding(q, exchange));


        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setRabbitAdmin(rabbitAdmin);
        container.setConnectionFactory(connectionFactory);
        container.setQueues(q);
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(autoAck ? AcknowledgeMode.AUTO : AcknowledgeMode.MANUAL);
        return container;
    }

    @Override
    public Class<?> getObjectType() {
        return SimpleMessageListenerContainer.class;
    }
}
