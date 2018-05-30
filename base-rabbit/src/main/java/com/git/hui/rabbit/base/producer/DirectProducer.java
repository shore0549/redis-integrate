package com.git.hui.rabbit.base.producer;

import com.rabbitmq.client.BuiltinExchangeType;

/**
 * Created by yihui in 21:35 18/5/27.
 */
public class DirectProducer {
    private static final String EXCHANGE_NAME = "direct.exchange";

    public void publishMsg(String routingKey, String msg) {
        try {
            MsgProducer.publishMsg(EXCHANGE_NAME, BuiltinExchangeType.DIRECT, routingKey, msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static void main(String[] args) {
        DirectProducer directProducer = new DirectProducer();
        String[] routingKey = new String[]{"test1", "test1"};
        String msg = "hello >>> ";


        for (int i = 0; i < 30; i++) {
            directProducer.publishMsg(routingKey[i % 2], msg + i);
        }
        System.out.println("----over-------");
    }
}
