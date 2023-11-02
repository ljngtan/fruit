package com.plant.fruit.pineapple.dto;

import lombok.Data;

import java.util.List;

@Data
public class ResourceDto {

    private Long id;

    private String path;

    private String description;

    private List<RoleDto> roles;

}
