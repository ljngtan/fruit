package com.plant.fruit.pineapple.service;

import com.plant.fruit.pineapple.dto.UserDto;

public interface UserService {

    UserDto createUser(UserDto userDto);

    UserDto updateUser(Long id, UserDto userDto);

    void deleteUser(Long id);

    UserDto getUserByUsername(String username);

}
