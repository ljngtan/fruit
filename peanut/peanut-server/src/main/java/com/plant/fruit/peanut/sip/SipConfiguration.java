package com.plant.fruit.peanut.sip;

import cn.hutool.core.thread.ThreadUtil;
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
import java.util.concurrent.Executor;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SipProperties.class)
public class SipConfiguration {

    private final SipProperties sipProperties;

    private final SipListener sipListener;

    private final InetUtils inetUtils;

    @Bean
    public Executor sipListenerExecutor() {
        return ThreadUtil.newFixedExecutor(sipProperties.getListenerThreadSize(), "sipListener-", true);
    }

    @Bean
    public Properties sipEnvironment() {
        Properties properties = new Properties();
        String ipAddress = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
        properties.setProperty(SipConstant.IP_ADDRESS, StrUtil.nullToDefault(sipProperties.getIp(), ipAddress));
        properties.setProperty(SipConstant.STACK_NAME, sipProperties.getStackName());
        properties.setProperty(SipConstant.THREAD_POOL_SIZE, StrUtil.toString(sipProperties.getMessageChannelSize()));
        properties.setProperty(SipConstant.LOG4J_LOGGER_NAME, "javax.sip.SipStack");
        properties.setProperty(SipConstant.TRACE_LEVEL, sipProperties.getLogLevel());
        return properties;
    }

    @Bean
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
