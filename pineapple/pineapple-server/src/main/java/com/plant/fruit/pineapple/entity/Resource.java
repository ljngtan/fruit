package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Resource {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 名字
     */
    private String name;

    /**
     * 权限
     */
    private String permission;

}
