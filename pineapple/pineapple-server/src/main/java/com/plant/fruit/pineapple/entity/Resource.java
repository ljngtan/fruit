package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Resource {

    @Id
    private Long id;

}
