package com.gizwits.pay.weixin.core;


/**
 * 分别对应商户模式、服务商模式
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public enum PayModel {
    BUSINESSMODEL("business", "商户模式"),
    SERVICEMODE("service", "服务商模式");

    private String code;
    private String name;

    PayModel(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static PayModel getPayModel(String code) {
        for (PayModel p : PayModel.values()) {
            if (p.getCode().equalsIgnoreCase(code)) {
                return p;
            }
        }
        return null;
    }


}