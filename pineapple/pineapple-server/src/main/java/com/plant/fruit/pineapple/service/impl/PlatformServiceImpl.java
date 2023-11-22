package com.plant.fruit.pineapple.service.impl;

import com.plant.fruit.pineapple.dto.UserDto;
import com.plant.fruit.pineapple.entity.User;
import com.plant.fruit.pineapple.mapper.UserMapper;
import com.plant.fruit.pineapple.repository.UserRepository;
import com.plant.fruit.pineapple.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class PlatformServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public void create(UserDto userDto) {
        User user = UserMapper.INSTANCE.dtoToEntity(userDto);
        user.setEnabled(true);
        userRepository.save(user);
    }

    @Override
    public void update(UserDto userDto) {
        User user = UserMapper.INSTANCE.dtoToEntity(userDto);
        userRepository.save(user);
    }

    @Override
    public void delete(Long[] ids) {
        userRepository.deleteAllById(Arrays.asList(ids));
    }

    @Override
    public Page<UserDto> page(UserDto userDto, PageRequest pageRequest) {
        return null;
    }

    @Override
    public UserDto getByUsername(String username) {
        User user = userRepository.findByUsername(username);
        return UserMapper.INSTANCE.entityToDto(user);
    }

}
