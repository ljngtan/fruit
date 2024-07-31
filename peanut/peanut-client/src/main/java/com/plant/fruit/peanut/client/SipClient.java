package com.plant.fruit.peanut.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("pineapple")
@RequestMapping("/resources")
public interface SipClient {
}
