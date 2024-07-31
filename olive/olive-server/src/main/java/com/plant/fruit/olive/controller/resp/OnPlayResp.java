package com.plant.fruit.olive.controller.resp;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class OnPlayResp {

    /**
     * 错误代码，0代表允许播放
     */
    private final Integer code;
    /**
     * 不允许播放时的错误提示
     */
    private final String msg;

}
