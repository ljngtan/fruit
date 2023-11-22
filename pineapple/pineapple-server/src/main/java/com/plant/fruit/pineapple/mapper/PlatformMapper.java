package com.plant.fruit.pineapple.mapper;

import com.plant.fruit.pineapple.dto.PlatformDto;
import com.plant.fruit.pineapple.entity.Platform;
import com.plant.fruit.waxberry.mapstruct.MapStructMapper;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PlatformMapper extends MapStructMapper<Platform, PlatformDto> {

    PlatformMapper INSTANCE = Mappers.getMapper(PlatformMapper.class);

}
