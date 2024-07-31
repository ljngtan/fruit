package com.plant.fruit.waxberry;

import com.plant.fruit.waxberry.validation.group.Save;
import com.plant.fruit.waxberry.validation.group.Update;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

public abstract class BaseController<ID, DTO, S extends BaseService<ID, DTO>> {

    @Resource
    protected S service;

    @PostMapping("/create")
    public void create(@RequestBody @Validated(Save.class) DTO dto) {
        service.create(dto);
    }

    @PostMapping("/update")
    public void update(@RequestBody @Validated(Update.class) DTO dto) {
        service.update(dto);
    }

    @PostMapping("/delete")
    public void delete(@NotEmpty ID[] ids) {
        service.delete(ids);
    }

}
