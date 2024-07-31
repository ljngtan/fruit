package com.plant.fruit.olive.controller;

import com.plant.fruit.olive.controller.req.*;
import com.plant.fruit.olive.controller.resp.OnPlayResp;
import com.plant.fruit.olive.controller.resp.OnServerStartedResp;
import com.plant.fruit.olive.service.ZlmHookService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 用于监听zlm事件.
 * <p>
 * Note: 只允许内网访问.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/zlm/hook")
public class ZlmHookController {

    private final ZlmHookService zlmHookService;

    /**
     * 流量统计事件，播放器或推流器断开时并且耗用流量超过特定阈值时会触发此事件，
     * 阈值通过配置文件general.flowThreshold配置；此事件对回复不敏感。
     */
    @PostMapping("/on_flow_report ")
    public Map<String, Object> on_flow_report() {
        return null;
    }

    /**
     * 访问http文件服务器上hls之外的文件时触发。
     */
    @PostMapping("/on_http_access ")
    public Map<String, Object> on_http_access() {
        return null;
    }

    /**
     * 播放器鉴权事件，rtsp/rtmp/http-flv/ws-flv/hls的播放都将触发此鉴权事件；
     * 如果流不存在，那么先触发on_play事件然后触发on_stream_not_found事件。
     * 播放rtsp流时，如果该流启动了rtsp专属鉴权(on_rtsp_realm)那么将不再触发on_play事件。
     */
    @PostMapping("/on_play ")
    public OnPlayResp on_play(@RequestBody OnPlayReq onPlayReq) {
        return zlmHookService.onPlay(onPlayReq);
    }

    /**
     * rtsp/rtmp/rtp推流鉴权事件。
     */
    @PostMapping("/on_publish ")
    public Map<String, Object> on_publish(@RequestBody OnPublishReq onPublishReq) {
        return null;
    }

    @PostMapping("/on_record_mp4 ")
    public Map<String, Object> on_record_mp4() {
        return null;
    }

    @PostMapping("/on_rtsp_auth ")
    public Map<String, Object> on_rtsp_auth() {
        return null;
    }

    @PostMapping("/on_rtsp_realm ")
    public Map<String, Object> on_rtsp_realm() {
        return null;
    }

    @PostMapping("/on_shell_login ")
    public Map<String, Object> on_shell_login() {
        return null;
    }

    @PostMapping("/on_stream_changed ")
    public Map<String, Object> on_stream_changed() {
        return null;
    }

    @PostMapping("/on_stream_none_reader ")
    public Map<String, Object> on_stream_none_reader() {
        return null;
    }

    /**
     * 流未找到事件
     */
    @SneakyThrows
    @PostMapping("/on_stream_not_found ")
    public Map<String, Object> on_stream_not_found(@RequestBody OnStreamNotFoundReq onStreamNotFoundReq) {

        return Map.of("code", 0, "msg", "success");
    }

    /**
     * 服务器启动事件，可以用于监听服务器崩溃重启；此事件对回复不敏感。
     */
    @PostMapping("/on_server_started ")
    public OnServerStartedResp onServerStarted(@RequestBody OnServerStartedReq onServerStartedReq) {
        zlmHookService.onServerStarted(onServerStartedReq);
        return new OnServerStartedResp(0, "success");
    }

    /**
     * 服务器定时上报时间，上报间隔可配置，默认 10s 上报一次
     */
    @PostMapping("/on_server_keepalive ")
    public void onServerKeepalive(@RequestBody OnServerKeepaliveReq onServerKeepaliveReq) {
        zlmHookService.onServerKeepLive(onServerKeepaliveReq);
    }

    @PostMapping("/on_rtp_server_timeout ")
    public Map<String, Object> on_rtp_server_timeout() {
        return null;
    }


}
