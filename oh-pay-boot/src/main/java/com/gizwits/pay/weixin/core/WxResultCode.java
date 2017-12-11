package com.gizwits.pay.weixin.core;


/**
 * 商户处理后同步返回给微信参数
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public enum WxResultCode {

    SUCCESS("SUCCESS", "OK"),
    ERROR("FAIL", "FAIL"),;

    private String code;

    private String msg;

    WxResultCode(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
