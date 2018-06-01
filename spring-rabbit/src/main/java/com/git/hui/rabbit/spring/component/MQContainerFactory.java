package com.git.hui.rabbit.spring.component;

import com.git.hui.rabbit.spring.component.consumer.IMqConsumer;
import lombok.Builder;
import lombok.Data;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.util.StringUtils;

/**
 * Created by yihui in 15:40 18/6/1.
 */
@Data
@Builder
public class MQContainerFactory implements FactoryBean<SimpleMessageListenerContainer> {
    private ExchangeType exchangeType;

    private String directExchange;
    private String topicExchange;
    private String fanoutExchange;

    private String queue;
    private String routingKey;


    private Boolean autoDeleted;
    private Boolean durable;
    private Boolean autoAck;

    private ConnectionFactory connectionFactory;
    private RabbitAdmin rabbitAdmin;
    private Integer concurrentNum;


    // 消费方
    private IMqConsumer consumer;


    private Exchange buildExchange() {
        if (directExchange != null) {
            exchangeType = ExchangeType.DIRECT;
            return new DirectExchange(directExchange);
        } else if (topicExchange != null) {
            exchangeType = ExchangeType.TOPIC;
            return new TopicExchange(topicExchange);
        } else if (fanoutExchange != null) {
            exchangeType = ExchangeType.FANOUT;
            return new FanoutExchange(fanoutExchange);
        } else {
            if (StringUtils.isEmpty(routingKey)) {
                throw new IllegalArgumentException("defaultExchange's routingKey should not be null!");
            }
            exchangeType = ExchangeType.DEFAULT;
            return new DirectExchange("");
        }
    }


    private Queue buildQueue() {
        if (StringUtils.isEmpty(queue)) {
            throw new IllegalArgumentException("queue name should not be null!");
        }

        return new Queue(queue, durable == null ? false : durable, false, autoDeleted == null ? true : autoDeleted);
    }


    private Binding bind(Queue queue, Exchange exchange) {
        return exchangeType.binding(queue, exchange, routingKey);
    }


    private void check() {
        if (rabbitAdmin == null || connectionFactory == null) {
            throw new IllegalArgumentException("rabbitAdmin and connectionFactory should not be null!");
        }
    }


    @Override
    public SimpleMessageListenerContainer getObject() throws Exception {
        check();

        Queue queue = buildQueue();
        Exchange exchange = buildExchange();
        Binding binding = bind(queue, exchange);

        rabbitAdmin.declareQueue(queue);
        rabbitAdmin.declareExchange(exchange);
        rabbitAdmin.declareBinding(binding);


        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setRabbitAdmin(rabbitAdmin);
        container.setConnectionFactory(connectionFactory);
        container.setQueues(queue);
        container.setPrefetchCount(20);
        container.setConcurrentConsumers(concurrentNum == null ? 1 : concurrentNum);
        container.setAcknowledgeMode(autoAck ? AcknowledgeMode.AUTO : AcknowledgeMode.MANUAL);


        if (consumer != null) {
            container.setMessageListener(consumer);
        }
        return container;
    }

    @Override
    public Class<?> getObjectType() {
        return SimpleMessageListenerContainer.class;
    }
}
