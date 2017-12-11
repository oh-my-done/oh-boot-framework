package com.gizwits.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@Entity
@Table(name = "g_account")
@EntityListeners(AuditingEntityListener.class)
public class AccountEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", columnDefinition = "int(20) COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 用于登陆的用户名
     */
    @Column(nullable = false, unique = true, columnDefinition = "varchar(100) COMMENT '用户名'")
    private String username;

    /**
     * 序列化的时候忽略 密码属性
     */
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "varchar(256) COMMENT '密码'")
    private String password;

    /**
     * 昵称
     */
    @Column(columnDefinition = "varchar(100) COMMENT '昵称'")
    private String nickName;

    /**
     * 真实姓名
     */
    @Column(columnDefinition = "varchar(100) COMMENT '真实姓名'")
    private String realName;

    /**
     * 邮箱
     */
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '邮箱地址'")
    private String email;

    /**
     * 手机号码
     */
    @Column(name = "phone", columnDefinition = "varchar(11) COMMENT '手机号码'")
    private String phone;

    /**
     * 性别
     */
    @Column(name = "gender", columnDefinition = "varchar(4) COMMENT '性别'")
    private String gender;

    /**
     * 生日
     */
    @Column(columnDefinition = "date COMMENT '生日'")
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date birthday;

    /**
     * 头像路径
     */
    @Column(length = 100, columnDefinition = "varchar(100) COMMENT '头像路径'")
    private String avatar;

    /**
     * 是否禁用
     */
    @Column(columnDefinition = "bool COMMENT '是否禁用 0:没有被禁用 1已被禁用'")
    private Boolean isEnable;

    /**
     * 创建时间
     */
    @CreatedDate
    @Column(columnDefinition = "datetime COMMENT '创建时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt;


    /**
     * 更新时间
     */

    @LastModifiedDate
    @Column(columnDefinition = "datetime COMMENT '更新时间'")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updatedAt;

    /**
     * 邮箱验证码
     */
    @Column(columnDefinition = "varchar(100)  COMMENT '邮箱验证码'")
    private String code;

    /**
     * 备注
     */
    @Column(columnDefinition = "TEXT COMMENT '备注'")
    private String remark;


    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "`g_account_roles`", joinColumns = {
            @JoinColumn(name = "account_id")}, inverseJoinColumns = {
            @JoinColumn(name = "role_id")})
    private Set<RoleEntity> roleEntities;

}
