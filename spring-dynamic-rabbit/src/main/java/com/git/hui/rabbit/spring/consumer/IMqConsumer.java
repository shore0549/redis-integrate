package com.git.hui.rabbit.spring.consumer;

import org.springframework.amqp.rabbit.core.ChannelAwareMessageListener;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;

/**
 * Created by yihui in 15:41 18/6/1.
 */
public interface IMqConsumer extends ChannelAwareMessageListener {

    void setContainer(SimpleMessageListenerContainer container);

    default void shutdown() {}

}
