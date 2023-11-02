package com.plant.fruit.pineapple.service.impl;

import com.plant.fruit.pineapple.dto.UserDto;
import com.plant.fruit.pineapple.entity.User;
import com.plant.fruit.pineapple.mapper.UserMapper;
import com.plant.fruit.pineapple.repository.UserRepository;
import com.plant.fruit.pineapple.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public UserDto createUser(UserDto userDto) {
        User user = UserMapper.INSTANCE.dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.entityToDto(savedUser);
    }

    @Override
    public UserDto updateUser(Long id, UserDto userDto) {
        User user = UserMapper.INSTANCE.dtoToEntity(userDto);
        User savedUser = userRepository.save(user);
        return UserMapper.INSTANCE.entityToDto(savedUser);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserMapper.INSTANCE.entityToDto(user);
    }

}
