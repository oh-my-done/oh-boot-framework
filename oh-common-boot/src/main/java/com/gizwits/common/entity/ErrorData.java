package com.gizwits.common.entity;

import lombok.Data;

import java.util.Date;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
public class ErrorData {
    private Date timestamp = new Date();
    private String path;
    public ErrorData(String path) {
        this.path = path;
    }
}
