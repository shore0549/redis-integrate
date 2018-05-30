package com.git.hui.rabbit.base.consumer;

import com.git.hui.rabbit.base.RabbitUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yihui in 20:06 18/5/29.
 */
public class MyDefaultConsumer {
    public void consumerMsg(String queue) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitUtil.getConnectionFactory();

        //创建连接
        Connection connection = factory.newConnection();

        //创建消息通道
        Channel channel = connection.createChannel();
        channel.queueDeclare(queue, true, false, true, null);
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                try {
                    System.out.println(" [ " + queue + " ] Received '" + message);
                    channel.basicAck(envelope.getDeliveryTag(), false);
                } catch (Exception e) {
                    channel.basicNack(envelope.getDeliveryTag(), false, true);
                }
            }
        };

        // 取消自动ack
        channel.basicConsume(queue, false, consumer);
    }

    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {
        MyDefaultConsumer consumer = new MyDefaultConsumer();
        consumer.consumerMsg("hello");

        Thread.sleep(1000 * 60 * 10);
    }
}
