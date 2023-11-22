package com.plant.fruit.peanut.sip.request.message;

import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.core.util.XmlUtil;
import com.plant.fruit.peanut.sip.request.message.notify.NotifyDocProcessor;
import com.plant.fruit.peanut.sip.SipConstant;
import gov.nist.javax.sip.RequestEventExt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.sip.SipProvider;
import javax.sip.message.Request;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class NotifyMessageResolver implements MessageResolver {

    private final Map<String, NotifyDocProcessor> notifyDocProcessors;

    @Override
    public void resolveMessage(RequestEventExt requestEvent, SipProvider sipProvider) {
        Request request = requestEvent.getRequest();
        byte[] rawContent = request.getRawContent();
        Map<String, Object> content = XmlUtil.xmlToMap(StrUtil.utf8Str(rawContent));
        Object cmdType = content.get("CmdType");
        if (!ObjUtil.isNull(cmdType)) {
            NotifyDocProcessor notifyDocProcessor = notifyDocProcessors.get(((String) cmdType).toLowerCase() + SipConstant.NOTIFY_DOC_PROCESSOR_CLASS_NAME_SUFFIX);
            if (ObjUtil.isNull(notifyDocProcessor)) {
                throw new NotifyDocTypeNotSupportedException((String) cmdType);
            }
            notifyDocProcessor.processNotifyDoc(requestEvent, content, sipProvider);
        }
    }

}
