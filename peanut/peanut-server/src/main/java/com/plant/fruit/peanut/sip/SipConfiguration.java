package com.plant.fruit.peanut.sip;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.commons.util.InetUtils;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sip.*;
import java.util.Properties;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SipProperties.class)
public class SipConfiguration {

    private final SipProperties sipProperties;

    private final SipListener sipListener;

    private final InetUtils inetUtils;

    @Bean
    public Properties sipEnvironment() {
        Properties properties = new Properties();
        properties.setProperty(SipConstant.IP_ADDRESS, StrUtil.blankToDefault(sipProperties.getIp(), inetUtils.findFirstNonLoopbackAddress().getHostAddress()));
        properties.setProperty(SipConstant.STACK_NAME, sipProperties.getStackName());
        properties.setProperty(SipConstant.LOG4J_LOGGER_NAME, sipProperties.getLoggerName());
        return properties;
    }

    // @Bean
    @SneakyThrows
    public SipProvider tlsSipProvider(Properties sipEnvironment) {
        SipFactory sipFactory = SipFactory.getInstance();
        SipStack sipStack = sipFactory.createSipStack(sipEnvironment);
        ListeningPoint listeningPoint = sipStack.createListeningPoint(sipStack.getIPAddress(),
                ObjUtil.defaultIfNull(sipProperties.getTlsPort(), ListeningPoint.PORT_5061), ListeningPoint.TLS);
        SipProvider sipProvider = sipStack.createSipProvider(listeningPoint);
        sipProvider.addSipListener(sipListener);
        return sipProvider;
    }

    @Bean
    @SneakyThrows
    public SipProvider tcpSipProvider(Properties sipEnvironment) {
        SipFactory sipFactory = SipFactory.getInstance();
        SipStack sipStack = sipFactory.createSipStack(sipEnvironment);
        ListeningPoint listeningPoint = sipStack.createListeningPoint(sipStack.getIPAddress(),
                ObjUtil.defaultIfNull(sipProperties.getPort(), ListeningPoint.PORT_5060), ListeningPoint.TCP);
        SipProvider sipProvider = sipStack.createSipProvider(listeningPoint);
        sipProvider.addSipListener(sipListener);
        return sipProvider;
    }

    @Bean
    @SneakyThrows
    public SipProvider udpSipProvider(Properties sipEnvironment) {
        SipFactory sipFactory = SipFactory.getInstance();
        SipStack sipStack = sipFactory.createSipStack(sipEnvironment);
        ListeningPoint listeningPoint = sipStack.createListeningPoint(sipStack.getIPAddress(),
                ObjUtil.defaultIfNull(sipProperties.getPort(), ListeningPoint.PORT_5060), ListeningPoint.UDP);
        SipProvider sipProvider = sipStack.createSipProvider(listeningPoint);
        sipProvider.addSipListener(sipListener);
        return sipProvider;
    }

}
