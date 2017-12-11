package com.gizwits.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Feel
 * @date 2017/11/13
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "系统状态码")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SysStatusCode {

    @ApiModelProperty(value = "业务状态响应码")
    private Integer code;
    @ApiModelProperty(value = "响应码消息")
    private String message;
}
