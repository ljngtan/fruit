package com.plant.fruit.peanut.sip.request.message.notify;

import gov.nist.javax.sip.RequestEventExt;

import javax.sip.SipProvider;
import java.util.Map;

public interface NotifyDocProcessor {

    void processNotifyDoc(RequestEventExt requestEvent, Map<String, Object> document, SipProvider sipProvider);

}
