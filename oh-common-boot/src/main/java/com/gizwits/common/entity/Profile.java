package com.gizwits.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Feel
 * @date 2017/11/23
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "系统信息")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Profile {
    @ApiModelProperty(value = "版本号")
    private String version;
}
