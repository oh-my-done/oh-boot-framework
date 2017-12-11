package com.gizwits.common.entity;

import com.google.common.collect.Lists;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 分页返回结果
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "分页查询")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult<T> {
    @ApiModelProperty(value = "总条数")
    private Long total;
    @ApiModelProperty(value = "当前页")
    private Long current;
    @ApiModelProperty(value = "每页大小")
    private Long size;
    @ApiModelProperty(value = "总页数")
    private Long pages;
    @ApiModelProperty(value = "前一页")
    private Long previous;
    @ApiModelProperty(value = "结果集")
    private List<T> results;


    public PageResult(long total, long current, long size, long pages, List<T> results) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = pages;
        if (current >= 1) {
            this.previous = current - 1;
        }

        if (results == null) {
            this.results = Lists.newArrayList();
        } else {
            this.results = results;
        }

    }

    public PageResult(long total, long current, long size, long pages) {
        this.total = total;
        this.current = current;
        this.size = size;
        this.pages = pages;
        if (current >= 1) {
            this.previous = current - 1;
        }
        this.results = Lists.newArrayList();
    }

}
