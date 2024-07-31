package com.plant.fruit.peanut.sip.request;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.plant.fruit.peanut.entity.Device;
import com.plant.fruit.peanut.repository.DeviceRepository;
import com.plant.fruit.peanut.sip.SipServiceException;
import com.plant.fruit.peanut.sip.SipUtils;
import com.plant.fruit.waxberry.mq.DelayedMessagePostProcessor;
import gov.nist.javax.sip.RequestEventExt;
import gov.nist.javax.sip.header.ParameterNames;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.stereotype.Component;

import javax.sip.ServerTransaction;
import javax.sip.SipFactory;
import javax.sip.address.URI;
import javax.sip.header.*;
import javax.sip.message.MessageFactory;
import javax.sip.message.Request;
import javax.sip.message.Response;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class RegisterRequestHandler implements SipRequestHandler {

    private final DeviceRepository deviceRepository;

    private final RedissonClient redissonClient;

    private final AmqpTemplate amqpTemplate;

    @Override
    @SneakyThrows
    public void handleRequest(RequestEventExt requestEvent) {
        HeaderFactory headerFactory = SipFactory.getInstance().createHeaderFactory();
        MessageFactory messageFactory = SipFactory.getInstance().createMessageFactory();
        Request request = requestEvent.getRequest();
        AuthorizationHeader authorizationHeader = (AuthorizationHeader) request.getHeader(AuthorizationHeader.NAME);
        if (Objects.nonNull(authorizationHeader)) {
            /*
             * HA1 = MD5(username:realm:passwd)
             * HA2 = MD5(Method:Uri)
             * if qop
             * response = MD5(HA1:nonce:nc:cNonce:qop:HA2)
             * else
             * response = MD5(HA1:nonce:HA2)
             */

            // 二阶段注册流程开始
            ExpiresHeader expiresHeader = request.getExpires();
            if (Objects.isNull(expiresHeader)) {
                throw new SipServiceException("Expires为空");
            }
            Response response;
            String username = authorizationHeader.getUsername();
            String realm = authorizationHeader.getRealm();
            URI uri = authorizationHeader.getURI();
            String nonce = authorizationHeader.getNonce();
            String nc = authorizationHeader.getParameter(ParameterNames.NC);
            String cNonce = authorizationHeader.getCNonce();
            String qop = authorizationHeader.getQop();
            Optional<Device> optionalDevice = deviceRepository.findById(username);
            RBucket<Object> bucket = redissonClient.getBucket("peanut:sip:client-keepalive:" + username);
            Device device = optionalDevice.orElseThrow(() -> {
                String errMsg = StrUtil.format("设备[{}]不存在!", username);
                return new SipServiceException(errMsg);
            });
            ViaHeader viaHeader = (ViaHeader) request.getHeader(ViaHeader.NAME);
            if (device.isOnline() && bucket.isExists()) {
                if (!StrUtil.equals(device.getIp(), viaHeader.getHost()) || !Objects.equals(device.getPort(), viaHeader.getPort())) {
                    String errMsg = StrUtil.format("设备[{}]已注册!", username);
                    throw new SipServiceException(errMsg);
                }
            }
            String password = device.getPassword();
            String ha1 = SecureUtil.md5().digestHex(username + ":" + realm + ":" + password);
            String ha2 = SecureUtil.md5().digestHex(Request.REGISTER + ":" + uri.toString());
            String kd;
            if (!StrUtil.isEmpty(qop)) {
                kd = SecureUtil.md5().digestHex(ha1 + ":" + nonce + ":" + nc + ":" + cNonce + ":" + qop + ":" + ha2);
            } else {
                kd = SecureUtil.md5().digestHex(ha1 + ":" + nonce + ":" + ha2);
            }
            if (StrUtil.equals(authorizationHeader.getResponse(), kd)) {
                int expires = expiresHeader.getExpires();
                if (expires > 0) {
                    device.setIp(viaHeader.getHost());
                    device.setPort(viaHeader.getPort());
                    device.setExpires(expires);
                    device.setDomain(realm);
                    device.setTransport(viaHeader.getTransport());
                    device.setOnline(true);
                    device.setOnlineTime(LocalDateTime.now());
                    bucket.set(Map.of("registerTime", System.currentTimeMillis(), "expires", expires));
                    bucket.expire(Duration.ofSeconds(expires));

                    amqpTemplate.convertAndSend("sip.direct", "afterRegister", device, DelayedMessagePostProcessor.of(1000));
                } else {
                    device.setOnline(false);
                    device.setOfflineTime(LocalDateTime.now());
                    device.setVoluntaryOffline(true);
                }
                deviceRepository.save(device);
                response = messageFactory.createResponse(Response.OK, request);
            } else {
                response = messageFactory.createResponse(Response.FORBIDDEN, request);
            }
            ServerTransaction serverTransaction = SipUtils.acquireServerTransaction(requestEvent);
            serverTransaction.sendResponse(response);
            // 二阶段注册流程结束
        } else {
            Response response = messageFactory.createResponse(Response.UNAUTHORIZED, request);
            WWWAuthenticateHeader wwwAuthenticateHeader = headerFactory.createWWWAuthenticateHeader(ParameterNames.DIGEST);
            wwwAuthenticateHeader.setRealm(SipUtils.acquireSipProvider(requestEvent).getSipStack().getStackName().substring(0, 10));
            wwwAuthenticateHeader.setQop("auth");
            wwwAuthenticateHeader.setNonce(IdUtil.fastSimpleUUID());
            wwwAuthenticateHeader.setAlgorithm("MD5");
            response.setHeader(wwwAuthenticateHeader);
            ServerTransaction serverTransaction = SipUtils.acquireServerTransaction(requestEvent);
            serverTransaction.sendResponse(response);
        }
    }

}
