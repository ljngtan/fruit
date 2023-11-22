package com.plant.fruit.pineapple.service;

import com.plant.fruit.pineapple.dto.UserDto;
import com.plant.fruit.waxberry.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PlatformService extends BaseService<Long, UserDto> {

    Page<UserDto> page(UserDto userDto, PageRequest pageRequest);

    UserDto getByUsername(String username);

}
