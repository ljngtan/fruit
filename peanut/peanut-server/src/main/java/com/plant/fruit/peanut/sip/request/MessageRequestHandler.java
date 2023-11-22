package com.plant.fruit.peanut.sip.request;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.plant.fruit.peanut.sip.request.message.MessageResolver;
import com.plant.fruit.peanut.sip.SipConstant;
import gov.nist.javax.sip.RequestEventExt;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.sip.message.Request;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class MessageRequestHandler implements SipRequestHandler {

    private final Map<String, MessageResolver> messageResolvers;

    @Override
    public void handleRequest(RequestEventExt requestEvent) {
        Request request = requestEvent.getRequest();
        byte[] rawContent = request.getRawContent();
        Document document = XmlUtil.parseXml(StrUtil.utf8Str(rawContent));
        Element rootElement = XmlUtil.getRootElement(document);
        String tagName = rootElement.getTagName();
        if (!StrUtil.isEmpty(tagName)) {
            MessageResolver messageResolver = messageResolvers.get(tagName.toLowerCase() + SipConstant.MESSAGE_RESOLVER_CLASS_NAME_SUFFIX);
            if (ObjUtil.isNull(messageResolver)) {
                throw new MessageTypeNotSupportedException(tagName);
            }
            messageResolver.resolveMessage(requestEvent, acquireSipProvider(requestEvent));
        }
    }

}
