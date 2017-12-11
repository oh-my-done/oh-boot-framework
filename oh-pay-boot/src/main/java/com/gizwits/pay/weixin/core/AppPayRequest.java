package com.gizwits.pay.weixin.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * app调起支付接口参数
 * 微信接口参数命名规范和java不要一样，为了保持一致映射和微信的参数一样
 * 正常统一下单接口返回数据
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "支付接口参数")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppPayRequest {

    /**
     * 应用ID
     */
    @ApiModelProperty(value = "应用ID")
    @JsonProperty("appid")
    private String appid;

    /**
     * 商户号
     */
    @ApiModelProperty(value = "商户号")
    @JsonProperty("partnerid")
    private String partnerid;

    /**
     * 预支付交易会话ID
     */
    @ApiModelProperty(value = "预支付交易会话ID")
    @JsonProperty("prepayid")
    private String prepayid;

    /**
     * 扩展字段
     */
    @ApiModelProperty(value = "扩展字段")
    @JsonProperty("package")
    private String packInfo;

    /**
     * 随机字符串
     */
    @ApiModelProperty(value = "随机字符串")
    @JsonProperty("noncestr")
    private String noncestr;

    /**
     * 时间戳
     */
    @ApiModelProperty(value = "时间戳")
    @JsonProperty("timestamp")
    private String timestamp;

    /**
     * 签名
     */
    @ApiModelProperty(value = "签名")
    @JsonProperty("sign")
    private String sign;
}
