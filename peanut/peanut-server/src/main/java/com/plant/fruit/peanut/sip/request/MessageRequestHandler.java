package com.plant.fruit.peanut.sip.request;

import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.plant.fruit.peanut.entity.Channel;
import com.plant.fruit.peanut.entity.Device;
import com.plant.fruit.peanut.repository.ChannelRepository;
import com.plant.fruit.peanut.repository.DeviceRepository;
import com.plant.fruit.peanut.sip.SipServiceException;
import com.plant.fruit.peanut.sip.SipUtils;
import gov.nist.javax.sip.RequestEventExt;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MessageRequestHandler implements SipRequestHandler {

    private final RedissonClient redissonClient;

    private final DeviceRepository deviceRepository;

    private final ChannelRepository channelRepository;

    @Override
    @SneakyThrows
    public void handleRequest(RequestEventExt requestEvent) {
        Request request = requestEvent.getRequest();
        byte[] rawContent = request.getRawContent();
        Document document = XmlUtil.parseXml(StrUtil.utf8Str(rawContent));
        Element rootElement = XmlUtil.getRootElement(document);
        String tagName = rootElement.getTagName();
        if (StrUtil.equals(tagName, "Response")) {
            doResponse(rootElement);
        } else if (StrUtil.equals(tagName, "Notify")) {
            doNotify(rootElement);
        } else if (StrUtil.equals(tagName, "Query")) {
            doQuery(rootElement);
        } else if (StrUtil.equals(tagName, "Control")) {
            doControl(rootElement);
        } else {
            throw new MessageTypeNotSupportedException(tagName);
        }
        MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
        Response response = messageFactory.createResponse(Response.OK, requestEvent.getRequest());
        ServerTransaction serverTransaction = SipUtils.acquireServerTransaction(requestEvent);
        serverTransaction.sendResponse(response);
    }

    private void doResponse(Element rootElement) {
        String cmdType = XmlUtil.elementText(rootElement, "CmdType");
        if (StrUtil.equals(cmdType, "DeviceInfo")) {
            String deviceId = XmlUtil.elementText(rootElement, "DeviceID");
            Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
            Device device = optionalDevice.orElseThrow(() -> {
                String errMsg = StrUtil.format("设备[{}]不存在!", deviceId);
                return new SipServiceException(errMsg);
            });
            String manufacturer = XmlUtil.elementText(rootElement, "Manufacturer");
            if (!StrUtil.isEmpty(manufacturer)) {
                device.setManufacturer(manufacturer);
            }
            String model = XmlUtil.elementText(rootElement, "Model");
            if (!StrUtil.isEmpty(model)) {
                device.setModel(model);
            }
            String firmware = XmlUtil.elementText(rootElement, "Firmware");
            if (!StrUtil.isEmpty(firmware)) {
                device.setFirmware(firmware);
            }
            String channel = XmlUtil.elementText(rootElement, "Channel");
            try {
                device.setChannel(Integer.valueOf(channel));
            } catch (NumberFormatException e) {
                String errMsg = StrUtil.format("通道数量为[{}]!", channel);
                throw new SipServiceException(errMsg, e);
            }
            deviceRepository.save(device);
        } else if (StrUtil.equals(cmdType, "Catalog")) {
            String deviceId = XmlUtil.elementText(rootElement, "DeviceID");
            Optional<Device> optionalDevice = deviceRepository.findById(deviceId);
            Device device = optionalDevice.orElseThrow(() -> {
                String errMsg = StrUtil.format("设备[{}]不存在!", deviceId);
                return new SipServiceException(errMsg);
            });
            String sumNum = XmlUtil.elementText(rootElement, "SumNum");
            if (!Objects.equals(device.getChannel(), Integer.valueOf(sumNum))) {
                throw new SipServiceException("Catalog与DeviceInfo的通道数量不匹配");
            }
            Element deviceListElement = XmlUtil.getElement(rootElement, "DeviceList");
            List<Element> itemElements = XmlUtil.getElements(deviceListElement, "Item");
            for (Element itemElement : itemElements) {
                String channelId = XmlUtil.elementText(itemElement, "DeviceID");
                Optional<Channel> optionalChannel = channelRepository.findById(channelId);
                if (optionalChannel.isEmpty()) {
                    Channel channel = new Channel();
                    channel.setId(channelId);
                    channel.setDeviceId(deviceId);
                    channelRepository.save(channel);
                } else {
                    Channel channel = optionalChannel.get();
                    if (!StrUtil.equals(deviceId, channel.getDeviceId())) {
                        channel.setDeviceId(deviceId);
                        channelRepository.save(channel);
                    }
                }
            }
        }
    }

    private void doNotify(Element rootElement) {
        String cmdType = XmlUtil.elementText(rootElement, "CmdType");
        if (StrUtil.equals(cmdType, "Keepalive")) {
            String status = XmlUtil.elementText(rootElement, "Status");
            if (StrUtil.equals("OK", status)) {
                String deviceId = XmlUtil.elementText(rootElement, "DeviceID");
                RBucket<Map<String, Object>> bucket = redissonClient.getBucket("peanut:sip:client-keepalive:" + deviceId);
                int expires = (int) bucket.get().get("expires");
                bucket.expire(Duration.ofSeconds(expires));
            } else {
                // TODO
            }
        } else if (StrUtil.equals(cmdType, "Alarm")) {

        }
    }

    private void doQuery(Element rootElement) {

    }

    private void doControl(Element rootElement) {

    }

}
