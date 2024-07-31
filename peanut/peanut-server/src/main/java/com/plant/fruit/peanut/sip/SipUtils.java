package com.plant.fruit.peanut.sip;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.spring.SpringUtil;
import com.plant.fruit.peanut.entity.Device;
import com.plant.fruit.waxberry.ProfileEnums;
import gov.nist.javax.sip.RequestEventExt;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.SipProvider;
import javax.sip.address.Address;
import javax.sip.address.AddressFactory;
import javax.sip.address.SipURI;
import javax.sip.header.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Slf4j
public abstract class SipUtils {

    public static final Map<String, String> CONTENT_TYPE_MANSCDP = Map.of("contentType", "application", "contentSubType", "manscdp+xml");

    public static final Map<String, String> CONTENT_TYPE_SDP = Map.of("contentType", "application", "contentSubType", "manscdp+xml");

    public static SipProvider acquireSipProvider(String transport) {
        return SpringUtil.getBean(transport.toLowerCase() + SipConstant.PROVIDER_CLASS_NAME_SUFFIX);
    }

    public static SipProvider acquireSipProvider(RequestEventExt requestEvent) {
        return (SipProvider) requestEvent.getSource();
    }

    @SneakyThrows
    public static ServerTransaction acquireServerTransaction(RequestEventExt requestEvent) {
        ServerTransaction serverTransaction = requestEvent.getServerTransaction();
        String activeProfile = SpringUtil.getActiveProfile().toUpperCase();
        if (ProfileEnums.valueOf(activeProfile).compareTo(ProfileEnums.PROD) < 0) {
            log.debug("当前RequestEvent中ServerTransaction为: {}", serverTransaction);
        }
        if (Objects.isNull(serverTransaction)) {
            serverTransaction = acquireSipProvider(requestEvent).getNewServerTransaction(requestEvent.getRequest());
        }
        return serverTransaction;
    }

    @SneakyThrows
    public static Request buildRequest(String method, String content, Map<String, String> contentType, Device device, SipProvider sipProvider) {
        AddressFactory addressFactory = SipFactory.getInstance().createAddressFactory();
        HeaderFactory headerFactory = SipFactory.getInstance().createHeaderFactory();
        MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();

        String localIP = sipProvider.getSipStack().getIPAddress();
        int localPort = sipProvider.getListeningPoints()[0].getPort();
        // From
        String fromHost = localIP + ":" + localPort;
        SipURI fromUri = addressFactory.createSipURI(sipProvider.getSipStack().getStackName(), fromHost);
        Address fromAddress = addressFactory.createAddress(fromUri);
        FromHeader from = headerFactory.createFromHeader(fromAddress, IdUtil.fastSimpleUUID());
        // To
        String toHost = device.getIp() + ":" + device.getPort();
        SipURI toUri = addressFactory.createSipURI(device.getId(), toHost);
        Address toAddress = addressFactory.createAddress(toUri);
        ToHeader to = headerFactory.createToHeader(toAddress, null); // 代表接收端的toTag必须由接收端设定，所以先设置成null
        // Via
        List<ViaHeader> viaList = new ArrayList<>();
        ViaHeader via = headerFactory.createViaHeader(localIP, localPort, device.getTransport(), null);
        viaList.add(via);
        // Max-Forwards
        MaxForwardsHeader maxForwards = headerFactory.createMaxForwardsHeader(70);
        // CSeq
        CSeqHeader cseq = headerFactory.createCSeqHeader(1L, method);
        // Call-ID
        CallIdHeader callId = sipProvider.getNewCallId();

        Request request = messageFactory.createRequest(toAddress.getURI(), method, callId, cseq, from, to, viaList, maxForwards);

        if (!StrUtil.isEmpty(content)) {
            ContentTypeHeader contentTypeHeader = headerFactory.createContentTypeHeader(contentType.get("contentType"), contentType.get("contentSubType"));
            request.setContent(content, contentTypeHeader);
        }

        return request;
    }

    public static String assembleCatalogQueryMessage(long sn, String deviceId) {
        String template = """
                    <?xml version="1.0" encoding="GB2312" ?>
                    <Query>
                        <CmdType>Catalog</CmdType>
                        <SN>{}</SN>
                        <DeviceID>{}</DeviceID>
                    </Query>
                """;
        return StrUtil.format(template, sn, deviceId);
    }

    public static String assembleDeviceInfoQueryMessage(long sn, String deviceId) {
        String template = """
                    <?xml version="1.0" encoding="GB2312" ?>
                    <Query>
                        <CmdType>DeviceInfo</CmdType>
                        <SN>{}</SN>
                        <DeviceID>{}</DeviceID>
                    </Query>
                """;
        return StrUtil.format(template, sn, deviceId);
    }

}
