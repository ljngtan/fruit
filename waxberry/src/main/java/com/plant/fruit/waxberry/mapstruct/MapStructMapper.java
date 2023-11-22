package com.plant.fruit.waxberry.mapstruct;

public interface MapStructMapper<T, DTO> {

    DTO entityToDto(T t);

    T dtoToEntity(DTO dto);

}
