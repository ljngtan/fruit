package com.plant.fruit.waxberry.mq;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

public class LogAfterReceiveMessagePostProcessor implements MessagePostProcessor {

    protected final Log logger = LogFactory.getLog(getClass());

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        if (logger.isDebugEnabled()) {
            logger.debug("Received message " + message);
        }
        return message;
    }

}
