package com.plant.fruit.waxberry.mq;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class DelayedMessagePostProcessor implements MessagePostProcessor {

    /**
     * The message will be delivered to the respective queues after x-delay milliseconds.
     */
    private final long delay;

    private DelayedMessagePostProcessor(long delay) {
        this.delay = delay;
    }

    /**
     * @param delay 延迟的毫秒数
     */
    public static DelayedMessagePostProcessor of(int delay) {
        return new DelayedMessagePostProcessor(delay);
    }

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        message.getMessageProperties().setDelayLong(delay);
        return message;
    }

}
