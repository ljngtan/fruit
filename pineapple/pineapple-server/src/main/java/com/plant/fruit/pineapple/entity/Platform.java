package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@Entity
@SQLDelete(sql = "UPDATE platform SET deleted = true WHERE id = ?") // 删除时做逻辑删除
@Where(clause = "deleted = false") // 查询时过滤已删除记录
public class Platform {

    @Id
    private String id;

    private String secret;

    private String name;

    private String desc;

    private boolean enabled;

    private Long createdBy;

    private LocalDateTime createdAt;

    private Long auditedBy;

    private LocalDateTime auditedAt;

    private Long lastModifiedBy;

    private LocalDateTime lastModifiedAt;

    private boolean deleted;

}
