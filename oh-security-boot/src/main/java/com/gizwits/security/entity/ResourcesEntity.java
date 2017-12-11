package com.gizwits.security.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@Entity
@Table(name = "g_resources")
@EntityListeners(AuditingEntityListener.class)
public class ResourcesEntity {

    /**
     * 主键ID
     */
    @Id
    @Column(name = "id", columnDefinition = "int(20) COMMENT '主键ID'")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    /**
     * 资源名称(一般对应restfulapi)
     */
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '资源名称'")
    private String name;


    /**
     * 资源详情
     */
    @Column(columnDefinition = "TEXT COMMENT '资源详情'")
    private String details;

    /**
     * 资源访问路径
     */
    @Column(nullable = false, columnDefinition = "varchar(100) COMMENT '资源访问路径'")
    private String url;


    /**
     * 方法
     */
    @Column(columnDefinition = "varchar(10)  default 'ALL'   COMMENT '方法:GET|POST|DELETE|PACTH|OPTIONS|PUT|ALL'")
    private String method;


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

}
