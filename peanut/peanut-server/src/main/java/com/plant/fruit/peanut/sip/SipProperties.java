package com.plant.fruit.peanut.sip;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("sip")
public class SipProperties {

    private String stackName = "gov.nist.javax.sip.SipStackImpl";

    private String ip;

    private Integer port;

    private Integer tlsPort;

    private String loggerName = "javax.sip.SipStack";

    private String stackLoggerClassName;

}
