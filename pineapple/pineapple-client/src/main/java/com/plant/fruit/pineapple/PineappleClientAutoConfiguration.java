package com.plant.fruit.pineapple;

import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cloud.openfeign.EnableFeignClients;

@AutoConfiguration
@ConditionalOnClass(EnableFeignClients.class)
@EnableFeignClients("com.plant.fruit.pineapple.client")
public class PineappleClientAutoConfiguration {
}
