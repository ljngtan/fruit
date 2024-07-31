package com.plant.fruit.olive.controller.resp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OnServerStartedResp {

    private final Integer code;

    private final String msg;

}
