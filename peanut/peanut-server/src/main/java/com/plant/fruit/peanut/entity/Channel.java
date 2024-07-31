package com.plant.fruit.peanut.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity
public class Channel {

    @Id
    private String id;

    private String deviceId;

}
