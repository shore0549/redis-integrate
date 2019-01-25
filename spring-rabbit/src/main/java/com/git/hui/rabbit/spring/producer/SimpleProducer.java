package com.git.hui.rabbit.spring.producer;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;

/**
 * Created by yihui in 19:53 18/5/30.
 */
public class SimpleProducer {

    public static void main(String[] args) throws InterruptedException {
        CachingConnectionFactory factory = new CachingConnectionFactory("127.0.0.1", 5672);
        factory.setUsername("admin");
        factory.setPassword("admin");
        factory.setVirtualHost("/");

        RabbitAdmin admin = new RabbitAdmin(factory);

        // 创建队列
        Queue queue = new Queue("hello", true, false, false, null);
        admin.declareQueue(queue);

        //创建topic类型的交换机
        TopicExchange exchange = new TopicExchange("topic.exchange");
        admin.declareExchange(exchange);

        //交换机和队列绑定，路由规则为匹配"foo."开头的路由键
        admin.declareBinding(BindingBuilder.bind(queue).to(exchange).with("foo.*"));


        //设置监听
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(factory);
        Object listener = new Object() {
            public void handleMessage(String foo) {
                System.out.println(" [x] Received '" + foo + "'");
            }
        };
        MessageListenerAdapter adapter = new MessageListenerAdapter(listener);
        container.setMessageListener(adapter);
        container.setQueues(queue);
        container.start();

        //发送消息
        RabbitTemplate template = new RabbitTemplate(factory);
        for (int i = 0; i < 10; i++) {
            template.convertAndSend("topic.exchange", "foo.bar", "Hello, world!");
        }
        Thread.sleep(1000);

        // 关闭
        container.stop();
    }

}
