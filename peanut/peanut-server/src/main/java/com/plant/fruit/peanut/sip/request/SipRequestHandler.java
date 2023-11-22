package com.plant.fruit.peanut.sip.request;

import gov.nist.javax.sip.RequestEventExt;

import javax.sip.SipProvider;

public interface SipRequestHandler {

    void handleRequest(RequestEventExt requestEvent);

    default SipProvider acquireSipProvider(RequestEventExt requestEvent) {
        return (SipProvider) requestEvent.getSource();
    }

}
