package com.git.hui.rabbit.spring.fac;

import com.git.hui.rabbit.spring.producer.AmqpProducer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by yihui in 12:07 18/5/30.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = FacSpringConfig.class)
public class FactoryComponentUnit {

    @Autowired
    private AmqpProducer amqpProducer;

    @Test
    public void testDirectConsumer() throws InterruptedException {
        String[] routingKey = new String[]{"hello.world", "fac-routing", "test1"};
        for (int i = 0; i < 1000; i++) {
            amqpProducer.publishMsg("fac.direct.exchange", routingKey[i % 3],
                    ">>> hello " + routingKey[i % 3] + ">>> " + i);
        }
        System.out.println("-------over---------");

        Thread.sleep(1000 * 60 * 10);
    }
}
