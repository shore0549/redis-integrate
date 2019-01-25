package com.git.hui.rabbit.spring;

import com.git.hui.rabbit.spring.consumer.DynamicConsumer;
import com.git.hui.rabbit.spring.factory.MQContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;

/**
 * Created by @author yihui in 14:26 19/1/25.
 */
public class ConsumerGenerate {

    /**
     * 创建消费者
     *
     * @param connectionFactory
     * @param rabbitAdmin
     * @param exchangeName
     * @param queueName
     * @param routingKey
     * @param autoDelete
     * @param durable
     * @param autoAck
     * @return
     * @throws Exception
     */
    public static DynamicConsumer genConsumer(ConnectionFactory connectionFactory, RabbitAdmin rabbitAdmin,
            String exchangeName, String queueName, String routingKey, boolean autoDelete, boolean durable,
            boolean autoAck) throws Exception {
        // exchangeType 这里演示direct传播方式
        MQContainerFactory fac =
                MQContainerFactory.builder().directExchange(exchangeName).queue(queueName).autoDeleted(autoDelete)
                        .autoAck(autoAck).durable(durable).routingKey(routingKey).rabbitAdmin(rabbitAdmin)
                        .connectionFactory(connectionFactory).build();
        return new DynamicConsumer(fac, queueName);
    }

}
