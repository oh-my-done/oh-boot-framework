package com.gizwits.pay.weixin.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;


/**
 * 支付成功返回数据
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
public class PayResponse {


    /**
     * 以下字段仅在微信h5支付返回.
     */
    private String appId;

    private String timeStamp;

    private String nonceStr;

    @JsonProperty("package")
    private String packAge;

    private String signType;

    private String paySign;

    /**
     * 以下字段在微信异步通知下返回.
     */
    private Double orderAmount;

    
    /**
     * 商户订单号
     */
    private String outTradeNo;
    /**
     * 微信支付订单号
     */
    private String transactionId;
}
