package com.plant.fruit.strawberry.security;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import cn.dev33.satoken.util.SaResult;
import cn.hutool.core.collection.CollUtil;
import com.plant.fruit.pineapple.client.ResourceClient;
import com.plant.fruit.pineapple.dto.ResourceDto;
import com.plant.fruit.pineapple.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import java.util.List;

@Configuration
@RequiredArgsConstructor
@EnableConfigurationProperties(SaTokenProperties.class)
public class SaTokenConfiguration {

    private final SaTokenProperties saTokenProperties;

    @Bean
    public SaReactorFilter saReactorFilter(ResourceClient resourceClient) {
        List<ResourceDto> resources = resourceClient.getResources();
        return new SaReactorFilter()
                .addInclude(saTokenProperties.getIncludes())
                .addExclude(saTokenProperties.getExcludes())
                .setBeforeAuth(obj -> {

                })
                .setAuth(obj -> {
                    for (ResourceDto resource : resources) {
                        List<RoleDto> roles = resource.getRoles();
                        if (!CollUtil.isEmpty(roles)) {
                            SaRouter.match(resource.getPath(), fun -> StpUtil.checkRoleOr(roles.stream().map(RoleDto::getName).toArray(String[]::new)));
                        }
                    }
                })
                .setError(e -> {
                    SaHolder.getResponse().setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
                    return SaResult.error(e.getMessage());
                });
    }

}
