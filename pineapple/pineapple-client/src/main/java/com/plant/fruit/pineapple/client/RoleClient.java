package com.plant.fruit.pineapple.client;

import com.plant.fruit.pineapple.dto.RoleDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@FeignClient("pineapple")
@RequestMapping("/roles")
public interface RoleClient {

    @GetMapping("/getRolesByUsername")
    List<RoleDto> getRolesByUsername(String username);

}
