package com.gizwits.pay.weixin.core;

/**
 * <p>
 * 交易类型枚举
 * 统一下单接口trade_type的传参可参考这里
 * JSAPI--公众号支付、小程序支付
 * NATIVE--原生扫码支付
 * APP--APP支付
 * MWEB--WAP支付
 * MICROPAY--刷卡支付,刷卡支付有单独的支付接口，不调用统一下单接口
 * </p>
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public enum TradeType {
    JSAPI, NATIVE, APP, WAP, MICROPAY, MWEB
}
