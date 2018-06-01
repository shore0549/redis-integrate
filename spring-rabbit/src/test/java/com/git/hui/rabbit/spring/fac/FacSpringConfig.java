package com.git.hui.rabbit.spring.fac;

import com.git.hui.rabbit.spring.component.MQContainerFactory;
import com.git.hui.rabbit.spring.producer.AmqpProducer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by yihui in 16:31 18/6/1.
 */
@Configuration
public class FacSpringConfig {

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public MQContainerFactory mqContainerFactory(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin) {
        return MQContainerFactory.builder().queue("fac.direct").directExchange("fac.direct.exchange").durable(true)
                .autoDeleted(false).autoAck(false).connectionFactory(connectionFactory).rabbitAdmin(rabbitAdmin)
                .routingKey("fac-routing").build();
    }

    @Bean
    public SimpleMessageListenerContainer facContainer(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin)
            throws Exception {
        MQContainerFactory fac = mqContainerFactory(connectionFactory, rabbitAdmin);
        SimpleMessageListenerContainer container = fac.getObject();
        container.setMessageListener(new FacMQConsumer("111"));
        return container;
    }

    @Bean
    public AmqpProducer amqpProducer() {
        return new AmqpProducer();
    }
}
