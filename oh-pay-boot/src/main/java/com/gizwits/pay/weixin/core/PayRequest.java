package com.gizwits.pay.weixin.core;

import lombok.Data;


/**
 * 支付统一下单和订单查询请求参数
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
public class PayRequest {

    /**
     * 支付方式.
     */
    private PayType payType;

  
    /**
     * 订单金额.
     */
    private Double orderAmount;


    /**
     * 微信openid, 仅微信支付时需要
     */
    private String openid;


    /**
     * ip
     */
    private String ip;

    /**
     * 商品描述
     * APP——需传入应用市场上的APP名字-实际商品名称，天天爱消除-游戏充值.
     */
    private String body;

    /**
     * 商品详情
     */
    private String detail;

    /**
     * 附加数据
     */
    private String attach;


    /**
     *  以下参数当微信查询订单的时候需要
     */

    /**
     * 商户号
     */
    private String mchId;


    /**
     * 微信订单号
     */
    private String transactionId;

    /**
     * 商户订单号
     */
    private String outTradeNo;

}