package com.plant.fruit.olive.service;

import com.plant.fruit.olive.controller.req.OnPlayReq;
import com.plant.fruit.olive.controller.req.OnServerKeepaliveReq;
import com.plant.fruit.olive.controller.req.OnServerStartedReq;
import com.plant.fruit.olive.controller.resp.OnPlayResp;

public interface ZlmHookService {

    void onFlowReport();

    void onHttpAccess();

    OnPlayResp onPlay(OnPlayReq onPlayReq);

    void onPublish();

    void onRecordMp4();

    void onRtspRealm();

    void onRtspAuth();

    void onShellLogin();

    void onStreamChanged();

    void onStreamNoneReader();

    void onStreamNotFound();

    void onServerStarted(OnServerStartedReq onServerStartedReq);

    void onServerKeepLive(OnServerKeepaliveReq onServerKeepaliveReq);

    void onRtpServerTimeout();

}
