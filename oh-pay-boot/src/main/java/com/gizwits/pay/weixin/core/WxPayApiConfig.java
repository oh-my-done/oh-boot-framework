package com.gizwits.pay.weixin.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


/**
 * <p>
 * 参考链接:
 * 1.https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_1
 * 2.https://pay.weixin.qq.com/wiki/doc/api/app/app_sl.php?chapter=9_1&index=1
 * 3.https://pay.weixin.qq.com/wiki/doc/api/app/app_sl.php?chapter=9_7&index=3
 * </p>
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */

@Slf4j
@Data
public class WxPayApiConfig {

    /**
     * 公众账号ID
     */
    @JsonProperty("appid")
    private String appId;
    /**
     * 微信支付商户号
     */
    @JsonProperty("mch_id")
    private String mchId;

    /**
     * 设备号
     */
    @JsonProperty("device_info")
    private String deviceInfo;


    /**
     * 随机字符串
     */
    @JsonProperty("nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    private String sign;

    /**
     * 签名类型
     */
    @JsonProperty("sign_type")
    private String signType;

    /**
     * 商品描述
     */
    private String body = "";

    /**
     * 商品详情
     */
    private String detail = "";

    /**
     * 附加数据
     */
    private String attach;


    /**
     * 商户订单号
     */
    @JsonProperty("out_trade_no")
    private String outTradeNo;


    /**
     * 货币类型
     */
    @JsonProperty("fee_type")
    private String feeType = "CNY";


    /**
     * 总金额,订单总金额,单位为分
     */
    @JsonProperty("total_fee")
    private Integer totalFee;


    /**
     * 终端IP,APP和网页支付提交用户端ip，Native支付填调用微信支付API的机器IP
     */
    @JsonProperty("spbill_create_ip")
    private String spbillCreateIp;

    /**
     * 交易起始时间
     */
    @JsonProperty("time_start")
    private String timeStart;


    /**
     * 交易结束时间
     */
    @JsonProperty("time_expire")
    private String timeExpire;


    /**
     * 订单优惠标记
     */
    @JsonProperty("goodsTag")
    private String goodsTag;


    /**
     * 通知地址.异步接收微信支付结果通知的回调地址，通知url必须为外网可访问的url，不能携带参数
     */
    @JsonProperty("notify_url")
    private String notifyUrl;


    /**
     * 交易类型枚举
     */
    @JsonProperty("trade_type")
    private TradeType tradeType;

    /**
     * 指定支付方式
     * 上传此参数no_credit--可限制用户不能使用信用卡支付
     */
    @JsonProperty("limit_pay")
    private String limitPay;


    @JsonProperty("product_id")
    private String productId;

    /**
     * 场景信息
     */
    @JsonProperty("scene_info")
    private String sceneInfo;


    /**
     * 模式
     */
    private PayModel payModel;

    /**
     * 服务商模式下的子商户应用ID
     * 子商户在微信开放平台上申请的APPID
     * 普通模式请不要配置，请在配置文件中将对应项删除
     */
    @JsonProperty("sub_appid")
    private String subAppId;

    /**
     * 子商户号
     * 服务商模式下的子商户号
     * 普通模式请不要配置，最好是请在配置文件中将对应项删除
     */
    @JsonProperty("sub_mch_id")
    private String subMchId;

    /**
     * 商户秘钥,用于加密签名
     */

    private String paternerSecret;


    /**
     * 微信支付订单号
     */
    private String transactionId;


    /**
     * 用户标识
     */
    private String openId;

    /**
     * 证书地址
     */
    private String certPath;


}
