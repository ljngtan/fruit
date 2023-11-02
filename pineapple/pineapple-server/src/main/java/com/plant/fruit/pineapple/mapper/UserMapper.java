package com.plant.fruit.pineapple.mapper;

import com.plant.fruit.pineapple.dto.UserDto;
import com.plant.fruit.pineapple.entity.User;
import com.plant.fruit.waxberry.mapstruct.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper extends MapStructMapper<User, UserDto> {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

}
