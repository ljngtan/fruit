package com.plant.fruit.olive.controller;

import com.plant.fruit.olive.service.DeviceOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/device_operation")
public class DeviceOperationController {

    private final DeviceOperationService deviceOperationService;

    @GetMapping("/play")
    public void play() {

    }

}
