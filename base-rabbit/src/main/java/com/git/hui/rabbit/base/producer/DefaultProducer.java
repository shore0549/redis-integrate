package com.git.hui.rabbit.base.producer;

import com.git.hui.rabbit.base.RabbitUtil;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * Created by yihui in 08:57 18/5/29.
 */
public class DefaultProducer {
    public static void publishMsg(String queue, String message) throws IOException, TimeoutException {
        ConnectionFactory factory = RabbitUtil.getConnectionFactory();

        //创建连接
        Connection connection = factory.newConnection();

        //创建消息通道
        Channel channel = connection.createChannel();
        //        channel.queueDeclare(queue, true, false, true, null);

        // 发布消息
        channel.basicPublish("", queue, null, message.getBytes());

        channel.close();
        connection.close();
    }

    public static void main(String[] args) throws IOException, TimeoutException {
        for (int i = 0; i < 20; i++) {
            publishMsg("hello", "msg" + i);
        }
    }
}
