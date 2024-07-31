package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Data
@Entity
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?") // 删除时做逻辑删除
@SQLRestriction("deleted = false") // 查询时过滤已删除记录
public class User {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 名字
     */
    private String name;

    /**
     * 手机号
     */
    private String mobile;

    /**
     * 电子邮箱
     */
    private String email;

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 逻辑删除控制字段
     */
    private boolean deleted;

}
