package com.plant.fruit.pineapple.controller;

import com.plant.fruit.pineapple.dto.UserDto;
import com.plant.fruit.pineapple.service.UserService;
import com.plant.fruit.waxberry.BaseController;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/user")
public class PlatformController extends BaseController<Long, UserDto, UserService> {

    @GetMapping("/page")
    public Page<UserDto> page(UserDto userDto, PageRequest pageRequest) {
        return null;
    }

    @GetMapping("/getByUsername")
    public UserDto getByUsername(@NotBlank String username) {
        return service.getByUsername(username);
    }

}
