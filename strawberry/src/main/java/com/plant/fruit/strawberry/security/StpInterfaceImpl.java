package com.plant.fruit.strawberry.security;

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.plant.fruit.pineapple.client.RoleClient;
import com.plant.fruit.pineapple.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.redisson.api.RList;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
public class StpInterfaceImpl implements StpInterface {

    private final RedissonClient redissonClient;

    private final RoleClient roleClient;

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return Collections.emptyList();
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        String listName = StrUtil.format("pineapple:users:{}:roles", loginId);
        RList<RoleDto> list = redissonClient.getList(listName);
        if (CollUtil.isEmpty(list)) {
            List<RoleDto> roles = roleClient.getRolesByUsername((String) loginId);
            if (CollUtil.isNotEmpty(roles)) {
                list.addAll(roles);
            }
        }
        return list.stream().map(RoleDto::getName).toList();
    }

}
