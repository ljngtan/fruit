package com.plant.fruit.peanut.client;

import com.plant.fruit.peanut.dto.DeviceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("peanut")
@RequestMapping("/devices")
public interface DeviceClient {

    @GetMapping
    DeviceDto getDevice(String deviceId);

}
