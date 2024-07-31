package com.plant.fruit.peanut.controller;

import com.plant.fruit.peanut.service.SipService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sip")
public class SipController {

    private final SipService sipService;

}
