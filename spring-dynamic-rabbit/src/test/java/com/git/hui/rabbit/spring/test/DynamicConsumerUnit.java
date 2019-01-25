package com.git.hui.rabbit.spring.test;

import com.git.hui.rabbit.spring.ConsumerGenerate;
import com.git.hui.rabbit.spring.DynSpringConfig;
import com.git.hui.rabbit.spring.consumer.DynamicConsumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui in 18:55 18/6/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DynSpringConfig.class)
public class DynamicConsumerUnit {

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private RabbitAdmin rabbitAdmin;

    @Autowired
    private DynSpringConfig.AmqpProducer amqpProducer;

    private static final String EXCHANGE = "fac.direct.exchange";

    @Test
    public void testGenConsumer() throws Exception {

        DynamicConsumer q1Consumer = ConsumerGenerate
                .genConsumer(connectionFactory, rabbitAdmin, EXCHANGE, "q1", "routingKey1", false, false, true);
        q1Consumer.start();

        DynamicConsumer q2Consumer = ConsumerGenerate
                .genConsumer(connectionFactory, rabbitAdmin, EXCHANGE, "q2", "routingKey2", false, false, true);
        q2Consumer.start();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                amqpProducer.publishMsg(EXCHANGE, "routingKey1", "hello: " + i);
            } else {
                amqpProducer.publishMsg(EXCHANGE, "routingKey2", "word: " + i);
            }
        }
        Thread.sleep(60 * 1000);

        q1Consumer.shutdown();
        q2Consumer.shutdown();
    }
}