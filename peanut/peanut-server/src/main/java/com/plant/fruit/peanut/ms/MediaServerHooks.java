package com.plant.fruit.peanut.ms;

import com.plant.fruit.peanut.ms.dto.OnStreamNotFoundReq;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/zl_media_kit/hook")
public class MediaServerHooks {

    @PostMapping("/on_flow_report ")
    public Map<String, Object> on_flow_report() {
        return null;
    }

    @PostMapping("/on_http_access ")
    public Map<String, Object> on_http_access() {
        return null;
    }

    @PostMapping("/on_play ")
    public Map<String, Object> on_play() {
        return null;
    }

    @PostMapping("/on_publish ")
    public Map<String, Object> on_publish() {
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
     *
     * @return {"code" : 0, "msg" : "success}
     */
    @PostMapping("/on_stream_not_found ")
    public Map<String, Object> on_stream_not_found(OnStreamNotFoundReq onStreamNotFoundReq) {
        

        return null;
    }

    @PostMapping("/on_server_started ")
    public Map<String, Object> on_server_started() {
        return null;
    }

    @PostMapping("/on_server_keepalive ")
    public Map<String, Object> on_server_keepalive() {
        return null;
    }

    @PostMapping("/on_rtp_server_timeout ")
    public Map<String, Object> on_rtp_server_timeout() {
        return null;
    }

}
