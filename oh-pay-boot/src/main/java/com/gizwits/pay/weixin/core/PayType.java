package com.gizwits.pay.weixin.core;


/**
 * 第三方支付方式
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public enum PayType {

    WXPAY_APP("微信App支付"),;

    private String name;

    PayType(String name) {
        this.name = name;
    }


    public String getName() {
        return name;
    }


    public static PayType getPayType(String name) {
        for (PayType c : PayType.values()) {
            if (c.getName().equals(name)) {
                return c;
            }
        }
        return null;
    }
}
