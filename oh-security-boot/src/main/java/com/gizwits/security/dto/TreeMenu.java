package com.gizwits.security.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 菜单树
 *
 * @author Feel
 * @date 2017/12/4
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "菜单树")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TreeMenu implements Comparable<TreeMenu> {

    @ApiModelProperty(value = "菜单ID")
    private Long id;

    @ApiModelProperty(value = "权限名称")
    private String authorityName;

    @ApiModelProperty(value = "菜单名称")
    private String menu;

    @ApiModelProperty(value = "父菜单ID")
    private String parentId;

    @ApiModelProperty(value = "菜单url")
    private String url;

    @ApiModelProperty(value = "菜单图标")
    private String icon;

    @ApiModelProperty(value = "菜单顺序")
    private int sort;

    @ApiModelProperty(value = "描述")
    private String remark;

    @ApiModelProperty(value = "子菜单")
    private List<TreeMenu> children;

    @Override
    public int compareTo(TreeMenu o) {

        if (this.getSort() != o.getSort()) {
            return this.getSort() - o.getSort();
        }

        return 0;
    }
}
