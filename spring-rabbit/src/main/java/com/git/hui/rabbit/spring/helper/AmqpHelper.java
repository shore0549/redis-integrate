package com.git.hui.rabbit.spring.helper;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * Created by yihui in 11:56 18/5/30.
 */
@Data
@AllArgsConstructor
public class AmqpHelper {
    private Queue queue;
    private TopicExchange exchange;
    private Binding binding;

    private SimpleMessageListenerContainer container;

}
