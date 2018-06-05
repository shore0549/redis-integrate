package com.git.hui.rabbit.spring.dynamic;

import com.git.hui.rabbit.spring.producer.AmqpProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
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
    private AmqpProducer amqpProducer;

    @Test
    public void testDirectConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"hello.world", "fac-routing", "test1"};
        for (int i = 0; i < 10; i++) {
            amqpProducer.publishMsg("fac.direct.exchange", routingKey[i % 3],
                    ">>> hello " + routingKey[i % 3] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }
}
