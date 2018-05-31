package com.git.hui.rabbit.spring;

import com.git.hui.rabbit.spring.producer.AmqpProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui in 12:07 18/5/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = SpringConfig.class)
public class SprintUnit {

    @Autowired
    private AmqpProducer amqpProducer;

    @Autowired
    private ApplicationContext applicationContext;

    @Test
    public void testDirectConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"hello.world", "world", "test1"};
        for (int i = 0; i < 10; i++) {
            amqpProducer
                    .publishMsg("direct.exchange", routingKey[i % 3], ">>> hello " + routingKey[i % 3] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }

    @Test
    public void testTopicConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"d.queue", "a.queue", "cqueue"};
        for (int i = 0; i < 20; i++) {
            amqpProducer.publishMsg("topic.exchange", routingKey[i % 3], ">>> hello " + routingKey[i % 3] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }


    @Test
    public void testFanoutConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"d.queue", "a.queue", "cqueue", "hello.world", "world", "test1"};
        for (int i = 0; i < 20; i++) {
            amqpProducer
                    .publishMsg("fanout.exchange", routingKey[i % 6], ">>> hello " + routingKey[i % 6] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }


    @Test
    public void justForTest() {
        System.out.println("-----");
    }
}
