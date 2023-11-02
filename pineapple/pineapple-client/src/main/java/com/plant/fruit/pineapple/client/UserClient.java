package com.plant.fruit.pineapple.client;

import com.plant.fruit.pineapple.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient("pineapple")
@RequestMapping("/users")
public interface UserClient {

    @GetMapping("/{username}")
    UserDto getUserByUsername(@PathVariable String username);

}
