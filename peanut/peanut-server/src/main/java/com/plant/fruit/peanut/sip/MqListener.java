package com.plant.fruit.peanut.sip;

import cn.hutool.core.thread.ThreadUtil;
import com.plant.fruit.peanut.entity.Device;
import lombok.SneakyThrows;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.sip.ClientTransaction;
import javax.sip.SipProvider;
import javax.sip.message.Request;

@Component
public class MqListener {

    @SneakyThrows
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(name = "afterRegister"),
            exchange = @Exchange(name = "sip.direct", delayed = Exchange.TRUE),
            key = "afterRegister"),
            concurrency = "1-12")
    public void afterRegister(Device device) {
        SipProvider sipProvider = SipUtils.acquireSipProvider(device.getTransport());
        // 设备信息查询开始
        String deviceInfoQueryMessage = SipUtils.assembleDeviceInfoQueryMessage(1L, device.getId());
        Request deviceInfoRequest = SipUtils.buildRequest(Request.MESSAGE, deviceInfoQueryMessage, SipUtils.CONTENT_TYPE_MANSCDP, device, sipProvider);
        ClientTransaction deviceInfoRequestClientTransaction = sipProvider.getNewClientTransaction(deviceInfoRequest);
        deviceInfoRequestClientTransaction.sendRequest();
        // 设备信息查询结束
        ThreadUtil.sleep(1000L);
        // 目录查询开始
        String catalogQueryMessage = SipUtils.assembleCatalogQueryMessage(1L, device.getId());
        Request catalogRequest = SipUtils.buildRequest(Request.MESSAGE, catalogQueryMessage, SipUtils.CONTENT_TYPE_MANSCDP, device, sipProvider);
        ClientTransaction catalogRequestClientTransaction = sipProvider.getNewClientTransaction(catalogRequest);
        catalogRequestClientTransaction.sendRequest();
        // 目录查询结束
    }

}
