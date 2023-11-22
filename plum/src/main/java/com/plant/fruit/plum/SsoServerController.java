package com.plant.fruit.plum;

import cn.dev33.satoken.config.SaSsoConfig;
import cn.dev33.satoken.secure.BCrypt;
import cn.dev33.satoken.sso.SaSsoProcessor;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.StrUtil;
import com.plant.fruit.pineapple.client.UserClient;
import com.plant.fruit.pineapple.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SsoServerController {

    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private final UserClient userClient;

    private volatile String userNotFoundEncodedPassword;

    @RequestMapping("/sso/*")
    public Object ssoRequest() {
        return SaSsoProcessor.instance.serverDister();
    }

    @Autowired
    private void configSso(SaSsoConfig sso, RestTemplateBuilder restTemplateBuilder) {
        prepareTimingAttackProtection();

        sso.setNotLoginView(() -> ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());

        sso.setDoLoginHandle((username, password) -> {
            username = StrUtil.trim(username);
            password = StrUtil.trim(password);

            UserDto user = userClient.getByUsername(username);
            if (ObjUtil.isNull(user)) {
                mitigateAgainstTimingAttack(password);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED);
            }

            StpUtil.login(username);
            return StpUtil.getTokenValue();
        });


        RestTemplate restTemplate = restTemplateBuilder.build();
        sso.setSendHttp(url -> restTemplate.getForObject(url, String.class));
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = BCrypt.hashpw(USER_NOT_FOUND_PASSWORD);
        }
    }

    private void mitigateAgainstTimingAttack(String rawPassword) {
        BCrypt.checkpw(rawPassword, this.userNotFoundEncodedPassword);
    }

}
