package com.plant.fruit.peanut.sip.request.message.notify;

import gov.nist.javax.sip.RequestEventExt;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.SipProvider;
import javax.sip.message.MessageFactory;
import javax.sip.message.Response;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class AlarmNotifyDocProcessor implements NotifyDocProcessor {

    private final RedissonClient redissonClient;

    @Override
    @SneakyThrows
    public void processNotifyDoc(RequestEventExt requestEvent, Map<String, Object> document, SipProvider sipProvider) {
        MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
        Response response = messageFactory.createResponse(Response.OK, requestEvent.getRequest());
        ServerTransaction serverTransaction = sipProvider.getNewServerTransaction(requestEvent.getRequest());
        serverTransaction.sendResponse(response);
    }

}
