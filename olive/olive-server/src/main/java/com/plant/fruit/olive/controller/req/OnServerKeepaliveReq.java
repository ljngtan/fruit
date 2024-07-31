package com.plant.fruit.olive.controller.req;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@SuppressWarnings("InnerClassMayBeStatic")
public class OnServerKeepaliveReq {

    private String mediaServerId;

    private Data data;


    @lombok.Data
    class Data {

        @JsonProperty("Buffer")
        private Integer buffer;

        @JsonProperty("BufferLikeString")
        private Integer bufferLikeString;

        @JsonProperty("BufferList")
        private Integer bufferList;

        @JsonProperty("BufferRaw")
        private Integer bufferRaw;

        @JsonProperty("Frame")
        private Integer frame;

        @JsonProperty("FrameImp")
        private Integer frameImp;

        @JsonProperty("MediaSource")
        private Integer mediaSource;

        @JsonProperty("MultiMediaSourceMuxer")
        private Integer multiMediaSourceMuxer;

        @JsonProperty("RtmpPacket")
        private Integer rtmpPacket;

        @JsonProperty("RtpPacket")
        private Integer rtpPacket;

        @JsonProperty("Socket")
        private Integer socket;

        @JsonProperty("TcpClient")
        private Integer tcpClient;

        @JsonProperty("TcpServer")
        private Integer tcpServer;

        @JsonProperty("TcpSession")
        private Integer tcpSession;

        @JsonProperty("UdpServer")
        private Integer udpServer;

        @JsonProperty("UdpSession")
        private Integer udpSession;

    }

}
