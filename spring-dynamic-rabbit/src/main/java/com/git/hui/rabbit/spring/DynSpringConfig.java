package com.git.hui.rabbit.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

/**
 * Created by yihui in 16:31 18/6/1.
 */
@Configuration
@PropertySource("classpath:application.properties")
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
    public AmqpProducer amqpProducer(ConnectionFactory connectionFactory) {
        return new AmqpProducer(connectionFactory);
    }

    public class AmqpProducer {

        private AmqpTemplate amqpTemplate;

        public AmqpProducer(ConnectionFactory connectionFactory) {
            amqpTemplate = new RabbitTemplate(connectionFactory);
        }

        /**
         * 将消息发送到指定的交换器上
         *
         * @param exchange
         * @param msg
         */
        public void publishMsg(String exchange, String routingKey, Object msg) {
            amqpTemplate.convertAndSend(exchange, routingKey, msg);
        }
    }
}
