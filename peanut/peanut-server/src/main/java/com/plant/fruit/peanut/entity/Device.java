package com.plant.fruit.peanut.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Device {

    /**
     * 设备编号
     */
    @Id
    private String id;

    /**
     * SIP域
     */
    private String domain;

    /**
     * 注册密码
     */
    private String password;

    /**
     * 传输协议
     */
    private String transport;

    /**
     * 设备ip
     */
    private String ip;

    /**
     * 设备侧SIP服务器端口
     */
    private Integer port;

    /**
     * 注册有效期
     */
    private Integer expires;

    /**
     * 是否在线
     */
    private boolean online;

    /**
     * 上线时间
     */
    private LocalDateTime onlineTime;

    /**
     * 离线时间
     */
    private LocalDateTime offlineTime;

    /**
     * 是否主动离线
     */
    private boolean voluntaryOffline;

    /**
     * 生产商
     */
    private String manufacturer;

    /**
     * 型号
     */
    private String model;

    /**
     * 固件版本
     */
    private String firmware;

    /**
     * 视频输入通道数
     */
    private Integer channel;

    /**
     * 乐观锁版本号
     */
    @Version
    private Long version;

}
