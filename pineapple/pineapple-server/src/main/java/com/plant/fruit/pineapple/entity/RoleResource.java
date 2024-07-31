package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class RoleResource {

    @Id
    @GeneratedValue
    private Long id;

    private Long roleId;

    private Long resourceId;

}
