package com.plant.fruit.pineapple.dto;

import lombok.Data;

import java.util.List;

@Data
public class RoleDto {

    private Long id;

    private String name;

    private String description;

    private List<ResourceDto> resources;

}
