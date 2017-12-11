package com.gizwits.common.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@ApiModel(description = "批量操作")
@AllArgsConstructor
@NoArgsConstructor
public class OperationdData<T, E> {

    @ApiModelProperty(value = "成功结果集")
    private List<T> success;
    @ApiModelProperty(value = "失败结果集")
    private List<E> error;

    public OperationdData(ArrayList<T> success, ArrayList<E> error) {
        this.success = success;
        this.error = error;
    }
}
