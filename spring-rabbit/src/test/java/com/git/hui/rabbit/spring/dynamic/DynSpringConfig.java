package com.git.hui.rabbit.spring.dynamic;

import com.git.hui.rabbit.spring.component.consumer.DynamicConsumer;
import com.git.hui.rabbit.spring.component.MQContainerFactory;
import com.git.hui.rabbit.spring.producer.AmqpProducer;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import javax.annotation.PostConstruct;

/**
 * Created by yihui in 16:31 18/6/1.
 */
@Configuration
@PropertySource("classpath:dynamicConfig.properties")
public class DynSpringConfig {

    @Autowired
    private Environment environment;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory factory = new CachingConnectionFactory();
        factory.setHost(environment.getProperty("dyn.mq.host"));
        factory.setPort(Integer.parseInt(environment.getProperty("dyn.mq.port")));
        factory.setUsername(environment.getProperty("dyn.mq.uname"));
        factory.setPassword(environment.getProperty("dyn.mq.pwd"));
        factory.setVirtualHost(environment.getProperty("dyn.mq.vhost"));
        return factory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        return new RabbitAdmin(connectionFactory);
    }

    @Bean
    public AmqpProducer amqpProducer() {
        return new AmqpProducer();
    }


//    @Bean
//    public RabbitAdmin rabbitAdmin() {
//        return new RabbitAdmin(connectionFactory());
//    }
//
//    @Bean
//    public DynamicConsumer dynamicConsumer(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin) {
//        MQContainerFactory fac = MQContainerFactory.builder().directExchange(environment.getProperty("dyn.mq.exchange"))
//                .queue(environment.getProperty("dyn.mq.queue"))
//                .autoDeleted(Boolean.parseBoolean(environment.getProperty("dyn.mq.autoDeleted")))
//                .autoAck(Boolean.parseBoolean(environment.getProperty("dyn.mq.autoAck")))
//                .durable(Boolean.parseBoolean(environment.getProperty("dyn.mq.durable")))
//                .routingKey(environment.getProperty("dyn.mq.routingKey")).rabbitAdmin(rabbitAdmin)
//                .connectionFactory(connectionFactory).build();
//
//        return new DynamicConsumer(fac);
//    }

    @Autowired
    private ConnectionFactory connectionFactory;

    @PostConstruct
    public void dynamicConsumer()
            throws Exception {
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        MQContainerFactory fac = MQContainerFactory.builder().directExchange(environment.getProperty("dyn.mq.exchange"))
                .queue(environment.getProperty("dyn.mq.queue"))
                .autoDeleted(Boolean.parseBoolean(environment.getProperty("dyn.mq.autoDeleted")))
                .autoAck(Boolean.parseBoolean(environment.getProperty("dyn.mq.autoAck")))
                .durable(Boolean.parseBoolean(environment.getProperty("dyn.mq.durable")))
                .routingKey(environment.getProperty("dyn.mq.routingKey")).rabbitAdmin(rabbitAdmin)
                .connectionFactory(connectionFactory).build();

        new DynamicConsumer(fac);
    }
}
