package com.plant.fruit.pineapple.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.time.LocalDateTime;

@Data
@Entity
@SQLDelete(sql = "UPDATE user SET deleted = true WHERE id = ?") // 删除时做逻辑删除
@Where(clause = "deleted = false") // 查询时过滤已删除记录
public class User {

    @Id
    @GeneratedValue
    private Long id;

    /**
     * 账号
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 姓名
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
     * 身份证号
     */
    private String idCardSn;

    /**
     * 身份证正面图片
     */
    private String idCardFrontSidePic;

    /**
     * 身份证背面图片
     */
    private String idCardBackSidePic;

    /**
     * 是否启用
     */
    private boolean enabled;

    /**
     * 创建人
     */
    private Long createdBy;

    /**
     * 创建时间
     */
    private LocalDateTime createdTime;

    /**
     * 上一次修改人
     */
    private Long lastModifiedBy;

    /**
     * 上一次修改时间
     */
    private LocalDateTime lastModifiedTime;

    /**
     * 逻辑删除控制字段
     */
    private boolean deleted;

}
