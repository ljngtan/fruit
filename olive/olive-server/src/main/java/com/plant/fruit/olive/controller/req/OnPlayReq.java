package com.plant.fruit.olive.controller.req;

import lombok.Data;

@Data
public class OnPlayReq {

    /**
     * 流应用名
     */
    private String app;

    /**
     * tcp链接唯一id
     */
    private String id;

    /**
     * 播放器ip
     */
    private String ip;

    /**
     * 播放url参数
     */
    private String params;

    /**
     * 播放器端口号
     */
    private Integer port;

    /**
     * 播放的协议，可能是rtsp、rtmp、http
     */
    private String schema;

    /**
     * 流id
     */
    private String stream;

    /**
     * 流虚拟主机
     */
    private String vhost;

    /**
     * 服务器id，通过配置文件设置
     */
    private String mediaServerId;
}
