package com.plant.fruit.peanut.context;

import cn.hutool.extra.spring.EnableSpringUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.plant.fruit.waxberry.mq.LogAfterReceiveMessagePostProcessor;
import com.plant.fruit.waxberry.mq.LogBeforeSendMessagePostProcessor;
import lombok.RequiredArgsConstructor;
import org.redisson.codec.TypedJsonJacksonCodec;
import org.redisson.spring.starter.RedissonAutoConfigurationCustomizer;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SimpleMessageConverter;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.autoconfigure.amqp.RabbitTemplateCustomizer;
import org.springframework.boot.task.TaskExecutorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@Configuration
@EnableSpringUtil
public class PeanutConfiguration {

    @Bean
    public RedissonAutoConfigurationCustomizer redissonCustomizer(ObjectMapper objectMapper) {
        return configuration -> configuration.setCodec(new TypedJsonJacksonCodec((Class<?>) null, objectMapper));
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper objectMapper) {
        Jackson2JsonMessageConverter jackson2JsonMessageConverter = new Jackson2JsonMessageConverter(objectMapper);
        ContentTypeDelegatingMessageConverter messageConverter = new ContentTypeDelegatingMessageConverter(jackson2JsonMessageConverter);
        messageConverter.addDelegate(MessageProperties.CONTENT_TYPE_TEXT_PLAIN, new SimpleMessageConverter());
        return messageConverter;
    }

    @Bean
    public RabbitTemplateCustomizer rabbitTemplateCustomizer() {
        return rabbitTemplate -> {
            rabbitTemplate.addBeforePublishPostProcessors(new LogBeforeSendMessagePostProcessor());
            rabbitTemplate.addAfterReceivePostProcessors(new LogAfterReceiveMessagePostProcessor());
        };
    }

    @EnableAsync
    @Configuration
    @RequiredArgsConstructor
    public static class AsyncConfiguration implements AsyncConfigurer {

        private final TaskExecutorBuilder taskExecutorBuilder;

        @Override
        public Executor getAsyncExecutor() {
            return taskExecutorBuilder.build();
        }

        @Override
        public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
            return new SimpleAsyncUncaughtExceptionHandler();
        }

    }

}
