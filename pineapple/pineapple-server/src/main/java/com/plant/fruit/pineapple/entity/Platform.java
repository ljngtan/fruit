package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
public class Platform {

    @Id
    private String id;

    private String secret;

    private String name;

    private String desc;

    private boolean enabled;

    private Long createdBy;

    private LocalDateTime createdTime;

    private Long auditedBy;

    private LocalDateTime auditedTime;

    private Long lastModifiedBy;

    private LocalDateTime lastModifiedTime;

}
