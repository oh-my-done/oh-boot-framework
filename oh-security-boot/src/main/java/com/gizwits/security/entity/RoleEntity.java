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
@Table(name = "g_role")
@EntityListeners(AuditingEntityListener.class)
public class RoleEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", columnDefinition = "int(20) COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 角色标志名
     */
    @Column(unique = true, nullable = false, columnDefinition = "varchar(50) COMMENT '角色标志名'")
    private String roleName;

    /**
     * 角色昵称
     */
    @Column(unique = true, columnDefinition = "varchar(100) COMMENT '角色昵称'")
    private String nickName;


    /**
     * 角色详情
     */
    @Column(columnDefinition = "TEXT COMMENT '角色详情'")
    private String details;


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
     * 备注
     */
    @Column(columnDefinition = "TEXT COMMENT '备注'")
    private String remark;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "`g_role_authorities`", joinColumns = {
            @JoinColumn(name = "role_id")}, inverseJoinColumns = {
            @JoinColumn(name = "authorities_id")})
    private Set<AuthorityEntity> authorityEntities;

}
