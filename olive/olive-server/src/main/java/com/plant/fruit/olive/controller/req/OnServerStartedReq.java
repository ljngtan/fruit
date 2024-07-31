package com.plant.fruit.olive.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class OnServerStartedReq {

    @JsonProperty("api.apiDebug")
    private String api$apiDebug;

    @JsonProperty("api.secret")
    private String api$secret;

    @JsonProperty("ffmpeg.bin")
    private String ffmpeg$bin;

    @JsonProperty("ffmpeg.cmd")
    private String ffmpeg$cmd;

    @JsonProperty("ffmpeg.log")
    private String ffmpeg$log;

    @JsonProperty("general.mediaServerId")
    private String general$mediaServerId;

    @JsonProperty("general.addMuteAudio")
    private String general$addMuteAudio;

    @JsonProperty("general.enableVhost")
    private String general$enableVhost;

    @JsonProperty("general.flowThreshold")
    private String general$flowThreshold;

    @JsonProperty("general.maxStreamWaitMS")
    private String general$maxStreamWaitMS;

    @JsonProperty("general.publishToHls")
    private String general$publishToHls;

    @JsonProperty("general.publishToMP4")
    private String general$publishToMP4;

    @JsonProperty("general.publishToRtxp")
    private String general$publishToRtxp;

    @JsonProperty("general.resetWhenRePlay")
    private String general$resetWhenRePlay;

    @JsonProperty("general.streamNoneReaderDelayMS")
    private String general$streamNoneReaderDelayMS;

    @JsonProperty("general.ultraLowDelay")
    private String general$ultraLowDelay;

    @JsonProperty("hls.fileBufSize")
    private String hls$fileBufSize;

    @JsonProperty("hls.filePath")
    private String hls$filePath;

    @JsonProperty("hls.segDur")
    private String hls$segDur;

    @JsonProperty("hls.segNum")
    private String hls$segNum;

    @JsonProperty("hls.segRetain")
    private String hls$segRetain;

    @JsonProperty("hook.admin_params")
    private String hook$admin_params;

    @JsonProperty("hook.enable")
    private String hook$enable;

    @JsonProperty("hook.on_flow_report")
    private String hook$on_flow_report;

    @JsonProperty("hook.on_http_access")
    private String hook$on_http_access;

    @JsonProperty("hook.on_play")
    private String hook$on_play;

    @JsonProperty("hook.on_publish")
    private String hook$on_publish;

    @JsonProperty("hook.on_record_mp4")
    private String hook$on_record_mp4;

    @JsonProperty("hook.on_rtsp_auth")
    private String hook$on_rtsp_auth;

    @JsonProperty("hook.on_rtsp_realm")
    private String hook$on_rtsp_realm;

    @JsonProperty("hook.on_server_started")
    private String hook$on_server_started;

    @JsonProperty("hook.on_shell_login")
    private String hook$on_shell_login;

    @JsonProperty("hook.on_stream_changed")
    private String hook$on_stream_changed;

    @JsonProperty("hook.on_stream_none_reader")
    private String hook$on_stream_none_reader;

    @JsonProperty("hook.on_stream_not_found")
    private String hook$on_stream_not_found;

    @JsonProperty("hook.timeoutSec")
    private String hook$timeoutSec;

    @JsonProperty("http.charSet")
    private String http$charSet;

    @JsonProperty("http.keepAliveSecond")
    private String http$keepAliveSecond;

    @JsonProperty("http.maxReqCount")
    private String http$maxReqCount;

    @JsonProperty("http.maxReqSize")
    private String http$maxReqSize;

    @JsonProperty("http.notFound")
    private String http$notFound;

    @JsonProperty("http.port")
    private String http$port;

    @JsonProperty("http.rootPath")
    private String http$rootPath;

    @JsonProperty("http.sendBufSize")
    private String http$sendBufSize;

    @JsonProperty("http.sslport")
    private String http$sslport;

    @JsonProperty("multicast.addrMax")
    private String multicast$addrMax;

    @JsonProperty("multicast.addrMin")
    private String multicast$addrMin;

    @JsonProperty("multicast.udpTTL")
    private String multicast$udpTTL;

    @JsonProperty("record.appName")
    private String record$appName;

    @JsonProperty("record.fastStart")
    private String record$fastStart;

    @JsonProperty("record.fileBufSize")
    private String record$fileBufSize;

    @JsonProperty("record.filePath")
    private String record$filePath;

    @JsonProperty("record.fileRepeat")
    private String record$fileRepeat;

    @JsonProperty("record.fileSecond")
    private String record$fileSecond;

    @JsonProperty("record.sampleMS")
    private String record$sampleMS;

    @JsonProperty("rtmp.handshakeSecond")
    private String rtmp$handshakeSecond;

    @JsonProperty("rtmp.keepAliveSecond")
    private String rtmp$keepAliveSecond;

    @JsonProperty("rtmp.modifyStamp")
    private String rtmp$modifyStamp;

    @JsonProperty("rtmp.port")
    private String rtmp$port;

    @JsonProperty("rtp.audioMtuSize")
    private String rtp$audioMtuSize;

    @JsonProperty("rtp.clearCount")
    private String rtp$clearCount;

    @JsonProperty("rtp.cycleMS")
    private String rtp$cycleMS;

    @JsonProperty("rtp.maxRtpCount")
    private String rtp$maxRtpCount;

    @JsonProperty("rtp.videoMtuSize")
    private String rtp$videoMtuSize;

    @JsonProperty("rtsp.authBasic")
    private String rtsp$authBasic;

    @JsonProperty("rtsp.directProxy")
    private String rtsp$directProxy;

    @JsonProperty("rtsp.handshakeSecond")
    private String rtsp$handshakeSecond;

    @JsonProperty("rtsp.keepAliveSecond")
    private String rtsp$keepAliveSecond;

    @JsonProperty("rtsp.modifyStamp")
    private String rtsp$modifyStamp;

    @JsonProperty("rtsp.port")
    private String rtsp$port;

    @JsonProperty("rtsp.sslport")
    private String rtsp$sslport;

    @JsonProperty("shell.maxReqSize")
    private String shell$maxReqSize;

    @JsonProperty("shell.port")
    private String shell$port;

}
