package com.plant.fruit.peanut.sip.request;

import gov.nist.javax.sip.RequestEventExt;

public interface SipRequestHandler {

    void handleRequest(RequestEventExt requestEvent);

}
