package com.git.hui.rabbit.spring.component;


import org.springframework.amqp.core.*;

/**
 * Created by yihui in 15:52 18/6/1.
 */
public enum ExchangeType {
    DIRECT {
        @Override
        public Binding binding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((DirectExchange) exchange).with(routingKey);
        }
    }, TOPIC {
        @Override
        public Binding binding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((TopicExchange) exchange).with(routingKey);
        }
    }, FANOUT {
        @Override
        public Binding binding(Queue queue, Exchange exchange, String routingKey) {
            return BindingBuilder.bind(queue).to((FanoutExchange) exchange);
        }
    }, DEFAULT {
        @Override
        public Binding binding(Queue queue, Exchange exchange, String routingKey) {
            // 对于Default而言，只能讲消息路由到名完全一直的queue上
            return BindingBuilder.bind(queue).to((DirectExchange) exchange).with(queue.getName());
        }
    };


    public abstract Binding binding(Queue queue, Exchange exchange, String routingKey);
}
