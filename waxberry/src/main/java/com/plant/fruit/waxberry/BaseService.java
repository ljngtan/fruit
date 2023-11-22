package com.plant.fruit.waxberry;

public interface BaseService<ID, DTO> {

    void create(DTO dto);

    void update(DTO dto);

    void delete(ID[] ids);

}
