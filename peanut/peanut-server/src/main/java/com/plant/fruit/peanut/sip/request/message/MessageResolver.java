package com.plant.fruit.peanut.sip.request.message;

import gov.nist.javax.sip.RequestEventExt;

import javax.sip.SipProvider;

public interface MessageResolver {

    void resolveMessage(RequestEventExt requestEvent, SipProvider sipProvider);

}
