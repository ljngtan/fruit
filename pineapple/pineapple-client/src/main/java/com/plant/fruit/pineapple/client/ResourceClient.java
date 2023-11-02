package com.plant.fruit.pineapple.client;

import com.plant.fruit.pineapple.dto.ResourceDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("pineapple")
@RequestMapping("/resources")
public interface ResourceClient {

    @GetMapping
    List<ResourceDto> getResources();

}
