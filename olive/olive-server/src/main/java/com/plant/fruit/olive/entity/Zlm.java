package com.plant.fruit.olive.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Zlm {

    @Id
    private String id;

    /**
     * 访问密钥
     */
    private String secret;

    /**
     * 内网ip
     */
    private String internalIp;

    /**
     * 外网ip
     */
    private String externalIp;

    /**
     * 外网域名
     */
    private String domain;

    /**
     * http端口
     */
    private String httpPort;

    /**
     * http ssl端口
     */
    private String httpSslPort;

    /**
     * rtsp端口
     */
    private String rtspPort;

    /**
     * rtsp ssl端口
     */
    private String rtspSslPort;

    /**
     * rtmp端口
     */
    private String rtmpPort;

    /**
     * 容量
     */
    private Integer capacity;

    /**
     * 是否在线
     */
    private boolean online;

    /**
     * 保活时间
     */
    private LocalDateTime keepaliveTime;

}
