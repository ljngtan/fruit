package com.plant.fruit.peanut.sip;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties("sip")
public class SipProperties {

    /**
     * SIP服务器编号
     */
    private String stackName;

    /**
     * SIP服务器IP
     */
    private String ip;

    /**
     * SIP服务器UDP/TCP端口
     */
    private Integer port;

    /**
     * SIP服务器TCP TLS端口
     */
    private Integer tlsPort;

    /**
     * 消息通道数
     */
    private Integer messageChannelSize = 12;

    /**
     * 监听器内线程数
     */
    private Integer listenerThreadSize = 120;

    /**
     * JAIN SIP包日志级别，默认ERROR，INFO会输出报文内容
     */
    private String logLevel = "ERROR";

}
