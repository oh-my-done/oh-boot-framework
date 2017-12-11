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
 * 权限表(用于控制菜单）
 *
 * @author Feel
 * @date 2017/12/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@Entity
@Table(name = "g_authorities")
@EntityListeners(AuditingEntityListener.class)
public class AuthorityEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", columnDefinition = "int(20) COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 权限名称
     */
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '权限名称'")
    private String name;


    /**
     * 菜单名称
     */
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '菜单名称'")
    private String menu;

    /**
     * 父级菜单
     */
    @Column(columnDefinition = "int(20) COMMENT '父级菜单ID'")
    private Long parentId;


    /**
     * 菜单logo
     */
    @Column(columnDefinition = "varchar(100) COMMENT '菜单LOGO'")
    private String icon;


    /**
     * 权限访问路径
     */
    @Column(columnDefinition = "varchar(100) COMMENT '权限访问路径'")
    private String url;


    /**
     * 菜单排序
     */
    @Column(columnDefinition = "int(11) COMMENT '菜单排序'")
    private Integer sort;

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
    @JoinTable(name = "`g_authorities_resources`", joinColumns = {
            @JoinColumn(name = "authorities_id")}, inverseJoinColumns = {
            @JoinColumn(name = "resources_id")})
    private Set<ResourcesEntity> resourcesEntities;
}
