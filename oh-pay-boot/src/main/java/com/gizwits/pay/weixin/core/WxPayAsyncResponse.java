package com.gizwits.pay.weixin.core;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import lombok.Data;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * 微信返回结果
 * 参考链接:https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_1
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@Root(name = "xml", strict = false)
public class WxPayAsyncResponse {

    /**
     * 返回状态码
     */
    @Element(name = "return_code", required = false)
    @XStreamAlias("return_code")
    private String returnCode;

    /**
     * 返回信息
     */
    @Element(name = "return_msg", required = false)
    @XStreamAlias("return_msg")
    private String returnMsg;

    /**
     * 以下字段在return_code为SUCCESS的时候有返回
     */

    /**
     * 应用ID
     */
    @Element(name = "appid", required = false)
    @XStreamAlias("appid")
    private String appId;

    /**
     * 商户号
     */
    @Element(name = "mch_id", required = false)
    @XStreamAlias("mch_id")
    private String mchId;

    /**
     * 子商户应用ID
     */
    @Element(name = "sub_appid", required = false)
    @XStreamAlias("sub_appid")
    private String subAppId;

    /**
     * 子商户号
     */
    @Element(name = "sub_mch_id", required = false)
    @XStreamAlias("sub_mch_id")
    private String subMchId;


    /**
     * 设备号
     */
    @Element(name = "device_info", required = false)
    @XStreamAlias("device_info")
    private String deviceInfo;

    /**
     * 随机字符串
     */
    @Element(name = "nonce_str", required = false)
    @XStreamAlias("nonce_str")
    private String nonceStr;

    /**
     * 签名
     */
    @Element(name = "sign", required = false)
    @XStreamAlias("sign")
    private String sign;

    /**
     * 业务结果
     */
    @Element(name = "result_code", required = false)
    @XStreamAlias("result_code")
    private String resultCode;

    /**
     * 错误代码
     */
    @Element(name = "err_code", required = false)
    @XStreamAlias("err_code")
    private String errCode;

    /**
     * 错误代码描述
     */
    @Element(name = "err_code_des", required = false)
    @XStreamAlias("err_code_des")
    private String errCodeDes;

    /**
     * 用户标识
     */
    @Element(name = "openid", required = false)
    @XStreamAlias("openid")
    private String openid;

    /**
     * 是否关注公众账号
     */
    @Element(name = "is_subscribe", required = false)
    @XStreamAlias("is_subscribe")
    private String isSubscribe;

    /**
     * 交易类型
     * 以下字段在return_code 和result_code都为SUCCESS的时候有返回.
     */
    @Element(name = "trade_type", required = false)
    @XStreamAlias("trade_type")
    private String tradeType;


    /**
     * 交易状态
     * SUCCESS—支付成功
     * REFUND—转入退款
     * NOTPAY—未支付
     * CLOSED—已关闭
     * REVOKED—已撤销（刷卡支付）
     * USERPAYING--用户支付中
     * PAYERROR--支付失败(其他原因，如银行返回失败)
     */
    @Element(name = "trade_state", required = false)
    @XStreamAlias("trade_state")
    private String tradeState;

    /**
     * 支付失败，请重新下单支付
     */
    @Element(name = "trade_state_desc", required = false)
    @XStreamAlias("trade_state_desc")
    private String tradeStateDesc;
    
    /**
     * 付款银行
     */
    @Element(name = "bank_type", required = false)
    @XStreamAlias("bank_type")
    private String bankType;

    /**
     * 预支付交易会话标识
     * 微信生成的预支付回话标识，用于后续接口调用中使用，该值有效期为2小时
     */
    @Element(name = "prepay_id", required = false)
    @XStreamAlias("prepay_id")
    private String prepayId;

    /**
     * 总金额
     */
    @Element(name = "total_fee", required = false)
    @XStreamAlias("total_fee")
    private Integer totalFee;

    /**
     * 货币种类
     */
    @Element(name = "fee_type", required = false)
    @XStreamAlias("fee_type")
    private String feeType;

    /**
     * 现金支付金额
     */
    @Element(name = "cash_fee", required = false)
    @XStreamAlias("cash_fee")
    private String cashFee;

    /**
     * 现金支付货币类型
     */
    @Element(name = "cash_fee_type", required = false)
    @XStreamAlias("cash_fee_type")
    private String cashFeeType;

    /**
     * 代金券金额
     */
    @Element(name = "coupon_fee", required = false)
    @XStreamAlias("coupon_fee")
    private String couponFee;

    /**
     * 代金券使用数量
     */
    @Element(name = "coupon_count", required = false)
    @XStreamAlias("coupon_count")
    private String couponCount;

    /**
     * 微信支付订单号
     */
    @Element(name = "transaction_id", required = false)
    @XStreamAlias("transaction_id")
    private String transactionId;

    /**
     * 商户订单号
     */
    @Element(name = "out_trade_no", required = false)
    @XStreamAlias("out_trade_no")
    private String outTradeNo;

    /**
     * 商家数据包
     */
    @Element(name = "attach", required = false)
    @XStreamAlias("attach")
    private String attach;

    /**
     * 支付完成时间
     */
    @Element(name = "time_end", required = false)
    @XStreamAlias("time_end")
    private String timeEnd;


    @Element(name = "short_url", required = false)
    @XStreamAlias("short_url")
    private String shortUrl;
}
