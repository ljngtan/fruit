package com.plant.fruit.olive.service.impl;

import com.plant.fruit.olive.controller.req.OnPlayReq;
import com.plant.fruit.olive.controller.req.OnServerKeepaliveReq;
import com.plant.fruit.olive.controller.req.OnServerStartedReq;
import com.plant.fruit.olive.controller.resp.OnPlayResp;
import com.plant.fruit.olive.entity.Zlm;
import com.plant.fruit.olive.repository.ZlmRepository;
import com.plant.fruit.olive.service.ZlmHookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ZlmHookServiceImpl implements ZlmHookService {

    private final ZlmRepository zlmRepository;

    @Override
    public void onFlowReport() {

    }

    @Override
    public void onHttpAccess() {

    }

    @Override
    public OnPlayResp onPlay(OnPlayReq onPlayReq) {
        String stream = onPlayReq.getStream();
        return null;
    }

    @Override
    public void onPublish() {

    }

    @Override
    public void onRecordMp4() {

    }

    @Override
    public void onRtspRealm() {

    }

    @Override
    public void onRtspAuth() {

    }

    @Override
    public void onShellLogin() {

    }

    @Override
    public void onStreamChanged() {

    }

    @Override
    public void onStreamNoneReader() {

    }

    @Override
    public void onStreamNotFound() {

    }

    @Override
    public void onServerStarted(OnServerStartedReq onServerStartedReq) {
        log.info("流媒体服务[{}]重启.", onServerStartedReq.getGeneral$mediaServerId());
    }

    @Override
    public void onServerKeepLive(OnServerKeepaliveReq onServerKeepaliveReq) {
        String mediaServerId = onServerKeepaliveReq.getMediaServerId();
        Optional<Zlm> optionalZlm = zlmRepository.findById(mediaServerId);
        if (optionalZlm.isPresent()) {
            Zlm zlm = optionalZlm.get();
            zlm.setOnline(true);
            zlm.setKeepaliveTime(LocalDateTime.now());
            log.info("流媒体服务[{}]保活.", mediaServerId);
            zlmRepository.save(zlm);
        }
    }

    @Override
    public void onRtpServerTimeout() {

    }
}
