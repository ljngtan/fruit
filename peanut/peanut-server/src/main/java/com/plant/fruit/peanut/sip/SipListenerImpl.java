package com.plant.fruit.peanut.sip;

import ch.qos.logback.core.CoreConstants;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.plant.fruit.peanut.sip.request.SipRequestHandler;
import gov.nist.javax.sip.DialogTimeoutEvent;
import gov.nist.javax.sip.RequestEventExt;
import gov.nist.javax.sip.SipListenerExt;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sip.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class SipListenerImpl implements SipListenerExt {

    private final Map<String, SipRequestHandler> requestHandlers;

    @Override
    public void processDialogTimeout(DialogTimeoutEvent timeoutEvent) {

    }

    @Override
    @SneakyThrows
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        log.debug("收到SIP请求: {}{}", CoreConstants.LINE_SEPARATOR, request);
        String method = request.getMethod();
        if (!StrUtil.isEmpty(method)) {
            SipRequestHandler sipRequestHandler = requestHandlers.get(method.toLowerCase() + SipConstant.REQUEST_HANDLER_CLASS_NAME_SUFFIX);
            try {
                if (ObjUtil.isNull(sipRequestHandler)) {
                    throw new SipMethodNotSupportedException(method);
                }
                sipRequestHandler.handleRequest((RequestEventExt) requestEvent);
            } catch (SipRuntimeException e) {
                log.warn("SIP请求处理失败: {}{}{}{}", e.getMessage(), CoreConstants.LINE_SEPARATOR, CoreConstants.LINE_SEPARATOR, request, e);
                MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
                Response response = messageFactory.createResponse(Response.BAD_REQUEST, request);
                SipProvider sipProvider = (SipProvider) requestEvent.getSource();
                ServerTransaction serverTransaction = sipProvider.getNewServerTransaction(request);
                serverTransaction.sendResponse(response);
            }
        }
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {

    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {

    }

    @Override
    public void processIOException(IOExceptionEvent exceptionEvent) {

    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {

    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {

    }

}
