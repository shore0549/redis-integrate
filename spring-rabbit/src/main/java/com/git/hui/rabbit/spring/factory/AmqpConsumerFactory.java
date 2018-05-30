package com.git.hui.rabbit.spring.factory;

import com.git.hui.rabbit.spring.helper.AmqpHelper;
import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.FactoryBean;


/**
 * Created by yihui in 11:36 18/5/30.
 */
@Data
@Builder
public class AmqpConsumerFactory implements FactoryBean<AmqpHelper> {
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

    private TopicExchange buildTopicExchange() {
        return new TopicExchange(exchange);
    }

    private Binding buildBinding(Queue queue, TopicExchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
    }

    @Override
    public AmqpHelper getObject() {
        Queue queue = buildQueue();
        queue.setAdminsThatShouldDeclare(rabbitAdmin);

        TopicExchange exchange = buildTopicExchange();
        exchange.setAdminsThatShouldDeclare(rabbitAdmin);

        Binding binding = buildBinding(queue, exchange);
        binding.setAdminsThatShouldDeclare(rabbitAdmin);


        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setRabbitAdmin(rabbitAdmin);
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setPrefetchCount(20);
        container.setAcknowledgeMode(autoAck ? AcknowledgeMode.AUTO : AcknowledgeMode.MANUAL);

        return new AmqpHelper(queue, exchange, binding, container);
    }

    @Override
    public Class<?> getObjectType() {
        return SimpleMessageListenerContainer.class;
    }
}
