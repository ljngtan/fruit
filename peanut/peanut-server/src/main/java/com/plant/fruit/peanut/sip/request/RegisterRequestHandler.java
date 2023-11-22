package com.plant.fruit.peanut.sip.request;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import gov.nist.javax.sip.RequestEventExt;
import gov.nist.javax.sip.header.ParameterNames;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.address.URI;
import javax.sip.header.AuthorizationHeader;
import javax.sip.header.HeaderFactory;
import javax.sip.header.ToHeader;
import javax.sip.header.WWWAuthenticateHeader;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.util.Objects;

@Slf4j
@Component
public class RegisterRequestHandler implements SipRequestHandler {

    @Override
    @SneakyThrows
    public void handleRequest(RequestEventExt requestEvent) {
        MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
        Request request = requestEvent.getRequest();
        AuthorizationHeader authorizationHeader = (AuthorizationHeader) request.getHeader(AuthorizationHeader.NAME);
        if (Objects.nonNull(authorizationHeader)) {
            // HA1 = MD5(username:realm:passwd)
            // HA2 = MD5(Method:Uri)
            // if qop
            // response = MD5(HA1:nonce:HA2)
            // else
            // response = MD5(HA1:nonce:nc:cnonce:qop:HA2)
            Response response;
            String username = authorizationHeader.getUsername();
            String realm = authorizationHeader.getRealm();
            URI uri = authorizationHeader.getURI();
            String nonce = authorizationHeader.getNonce();
            String nc = authorizationHeader.getParameter(ParameterNames.NC);
            String cnonce = authorizationHeader.getCNonce();
            String qop = authorizationHeader.getQop();
            String ha1 = SecureUtil.md5().digestHex(username + ":" + realm + ":" + "12345678");
            String ha2 = SecureUtil.md5().digestHex(Request.REGISTER + ":" + uri.toString());
            if (StrUtil.equals(authorizationHeader.getResponse(), SecureUtil.md5().digestHex(ha1 + ":" + nonce + ":" + nc + ":" + cnonce + ":" + qop + ":" + ha2))) {
                response = messageFactory.createResponse(Response.OK, request);
            } else {
                response = messageFactory.createResponse(Response.FORBIDDEN, request);
            }
            ServerTransaction serverTransaction = acquireSipProvider(requestEvent).getNewServerTransaction(request);
            serverTransaction.sendResponse(response);
        } else {
            Response response = messageFactory.createResponse(Response.UNAUTHORIZED, request);
            ToHeader toHeader = (ToHeader) response.getHeader(ToHeader.NAME);
            toHeader.setTag(IdUtil.fastSimpleUUID());
            HeaderFactory headerFactory = SipFactory.getInstance().createHeaderFactory();
            WWWAuthenticateHeader wwwAuthenticateHeader = headerFactory.createWWWAuthenticateHeader(ParameterNames.DIGEST);
            wwwAuthenticateHeader.setRealm("3402000000");
            wwwAuthenticateHeader.setQop("auth");
            wwwAuthenticateHeader.setNonce(IdUtil.fastSimpleUUID());
            wwwAuthenticateHeader.setAlgorithm("MD5");
            response.setHeader(wwwAuthenticateHeader);
            ServerTransaction serverTransaction = acquireSipProvider(requestEvent).getNewServerTransaction(request);
            serverTransaction.sendResponse(response);
        }
    }

}
