package com.plant.fruit.peanut.sip;

import ch.qos.logback.core.CoreConstants;
import cn.hutool.json.JSONUtil;
import com.plant.fruit.peanut.sip.request.SipRequestHandler;
import gov.nist.javax.sip.DialogTimeoutEvent;
import gov.nist.javax.sip.RequestEventExt;
import gov.nist.javax.sip.SipListenerExt;
import gov.nist.javax.sip.header.RetryAfter;
import gov.nist.javax.sip.message.MessageFactoryImpl;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.sip.*;
import javax.sip.header.ServerHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.util.Map;
import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
@Async("sipListenerExecutor")
public class SipListenerImpl implements SipListenerExt {

    private final Map<String, SipRequestHandler> requestHandlers;

    @Override
    public void processDialogTimeout(DialogTimeoutEvent timeoutEvent) {
        log.debug("监测到对话超时: {}{}", CoreConstants.LINE_SEPARATOR, JSONUtil.toJsonPrettyStr(timeoutEvent));
    }

    @Override
    @SneakyThrows
    public void processRequest(RequestEvent requestEvent) {
        Request request = requestEvent.getRequest();
        log.debug("收到SIP请求: {}{}", CoreConstants.LINE_SEPARATOR, request);
        String method = request.getMethod();
        SipRequestHandler sipRequestHandler = requestHandlers.get(method.toLowerCase() + SipConstant.REQUEST_HANDLER_CLASS_NAME_SUFFIX);
        try {
            if (Objects.isNull(sipRequestHandler)) {
                throw new SipMethodNotSupportedException(method);
            }
            sipRequestHandler.handleRequest((RequestEventExt) requestEvent);
        } catch (SipRuntimeException e) {
            log.warn("SIP请求处理失败: {}{}{}{}", e.getMessage(), CoreConstants.LINE_SEPARATOR, CoreConstants.LINE_SEPARATOR, request, e);
            MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
            Response response = messageFactory.createResponse(Response.BAD_REQUEST, request);
            response.setReasonPhrase(e.getMessage());
            ServerTransaction serverTransaction = SipUtils.acquireServerTransaction((RequestEventExt) requestEvent);
            serverTransaction.sendResponse(response);
        } catch (Exception e) {
            log.error("SIP请求处理失败: {}{}{}{}", e.getMessage(), CoreConstants.LINE_SEPARATOR, CoreConstants.LINE_SEPARATOR, request, e);
            MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
            Response response = messageFactory.createResponse(Response.SERVER_INTERNAL_ERROR, request);
            response.setReasonPhrase(e.getMessage());
            if (MessageFactoryImpl.getDefaultServerHeader() != null) {
                ServerHeader serverHeader = MessageFactoryImpl
                        .getDefaultServerHeader();
                response.setHeader(serverHeader);
            }
            RetryAfter retryAfter = new RetryAfter();
            retryAfter.setRetryAfter(300);
            response.setHeader(retryAfter);
            ServerTransaction serverTransaction = SipUtils.acquireServerTransaction((RequestEventExt) requestEvent);
            serverTransaction.sendResponse(response);
        }
    }

    @Override
    public void processResponse(ResponseEvent responseEvent) {
        Response response = responseEvent.getResponse();
        log.debug("收到SIP响应: {}{}", CoreConstants.LINE_SEPARATOR, response);
    }

    @Override
    public void processTimeout(TimeoutEvent timeoutEvent) {
        log.debug("监测到超时: {}{}", CoreConstants.LINE_SEPARATOR, JSONUtil.toJsonPrettyStr(timeoutEvent));
    }

    @Override
    public void processIOException(IOExceptionEvent exceptionEvent) {
        log.debug("监测到IO异常: {}{}", CoreConstants.LINE_SEPARATOR, JSONUtil.toJsonPrettyStr(exceptionEvent));
    }

    @Override
    public void processTransactionTerminated(TransactionTerminatedEvent transactionTerminatedEvent) {
        log.debug("监测到事务死亡: {}{}", CoreConstants.LINE_SEPARATOR, JSONUtil.toJsonPrettyStr(transactionTerminatedEvent));
    }

    @Override
    public void processDialogTerminated(DialogTerminatedEvent dialogTerminatedEvent) {
        log.debug("监测到对话死亡: {}{}", CoreConstants.LINE_SEPARATOR, JSONUtil.toJsonPrettyStr(dialogTerminatedEvent));
    }

}
