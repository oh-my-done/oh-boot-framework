package com.gizwits.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "数据请求响应体")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    @ApiModelProperty(value = "业务状态响应码")
    private Integer code;
    @ApiModelProperty(value = "业务状态响应消息")
    private String message;
    @ApiModelProperty(value = "业务返回数据")
    private T data;

    public ApiResponse(T data) {
        this.code = ResponseCode.SUCCESS.getCode();
        this.message = ResponseCode.SUCCESS.getName();
        this.data = data;
    }

    public ApiResponse(Integer code, String message) {
        this.code = code;
        this.message = message;
        this.data = (T) new HashMap();
    }


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<T>(data);
    }


    public static <T> ApiResponse<T> error(T data) {
        return new ApiResponse<T>(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getName(), data);
    }

    public static <T> ApiResponse<T> error() {
        return new ApiResponse(ResponseCode.ERROR.getCode(), ResponseCode.ERROR.getName());
    }

}
