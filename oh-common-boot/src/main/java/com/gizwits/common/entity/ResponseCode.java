package com.gizwits.common.entity;

/**
 * <p>
 * 业务状态码分配规则
 * 1. 前100位给系统分配使用
 * 2. 每个业务模块预留20位状态码
 * 3. 总共一个业务系统中可容纳45个业务模块（这个量基本可以覆盖所有的模块)
 * </p>
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public enum ResponseCode {

    /**
     * code=[9001-9100]
     * 系统状态码
     */
    SUCCESS(9001, "成功"),
    ERROR(9002, "失败"),
    NO_PERMISSON(9003, "没有权限访问"),
    AUTH_SUCCESS(9004, "获取token成功"),
    AUTH_ERROR(9005, "获取token失败"),
    SERVER_ERROR(9006, "服务端内部错误"),
    API_NO_FOUND(9007, "访问接口不存在"),
    TOKEN_NOT_EMPTY(9008, "访问token不能为空"),
    REFRESH_TOKEN_ERROR(9009, "token刷新失败"),

    LOGIN_USERNAME_ERROR(9010, "用户不存在"),
    LOGIN_PASSWORD_ERROR(9011, "密码错误"),
    SQLEXCEPTION(9020, "数据库操作异常"),
    EXCEPTION(9021, "自定义异常"),
    NOTVALIDEXCEPTION(9022, "参数验证失败"),
    INVALID_ERROR(9050, "非法格式"),
    INVALID_EMPTY(9051, "内容为空"),

    /**
     * code=[9101-9120]
     *
     */


    /**
     * code=[9121-9140]
     * 支付模块状态码
     */
    UNIFIEDORDER_SUCCESS(9121, "预支付交易单成功"),
    UNIFIEDORDER_ERROR(9122, "预支付交易单失败"),
    PREPAY_SIGN_SUCCESS(9123, "预支付重签名成功"),
    PREPAY_SIGN_ERROR(9124, "预支付重签名失败"),


    /**
     * code=[9141-9160]
     * 订单模块状态码
     */
    ORDER_SUCCESS(9091, "订单下单成功"),
    ORDER_ERROR(9092, "订单下单失败"),
    ORDER_NOT_FOUND(9093, "订单不存在"),
    ORDER_PAY_ERROR(9094, "订单支付失败,已回滚");

    /**
     * code=[9161-9180]
     */

    /**
     * code=[9181-9200]
     *
     */

    /**
     * code=[9201-9220]
     *
     */

    /**
     * code=[9221-9240]
     *
     */


    /**
     * code=[9241-9260]
     *
     */


    /**
     * code=[9261-9280]
     */


    private int code;
    private String name;

    /**
     * 9001 固定表示服务码
     *
     * @param code
     * @param name
     */
    ResponseCode(int code, String name) {
        this.code = 9001 * 10000 + code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static ResponseCode getResponseCode(String name) {
        for (ResponseCode c : ResponseCode.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }

}
