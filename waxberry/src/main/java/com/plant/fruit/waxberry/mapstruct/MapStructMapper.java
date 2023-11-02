package com.plant.fruit.waxberry.mapstruct;

public interface MapStructMapper<ENTITY, DTO> {

    DTO entityToDto(ENTITY entity);

    ENTITY dtoToEntity(DTO dto);

}
