package com.gizwits.pay.weixin.core;

import com.gizwits.common.utils.CommonUtils;
import com.gizwits.pay.weixin.execption.PayIllegalArgumentException;
import com.gizwits.pay.weixin.util.MoneyUtil;
import com.gizwits.pay.weixin.util.XmlUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Slf4j
public class WxPayService {

    private WxPayApiConfig wxPayApiConfig;

    public WxPayService(WxPayApiConfig wxPayApiConfig) {
        this.wxPayApiConfig = wxPayApiConfig;
    }


    /**
     * 统一下单
     * <p>
     * 调起支付接口,封装调起微信支付的参数 https://pay.weixin.qq.com/wiki/doc/api/app/app.php?chapter=9_12
     *
     * @param payRequest
     * @return
     */
    public AppPayRequest prePay(PayRequest payRequest) {

        wxPayApiConfig.setSpbillCreateIp(payRequest.getIp());
        //元转分
        wxPayApiConfig.setTotalFee(MoneyUtil.Yuan2Fen(payRequest.getOrderAmount()));
        wxPayApiConfig.setTradeType(TradeType.APP);
        wxPayApiConfig.setOutTradeNo(payRequest.getOutTradeNo());
        wxPayApiConfig.setDetail(payRequest.getDetail());
        wxPayApiConfig.setBody(payRequest.getBody());
        wxPayApiConfig.setAttach(payRequest.getAttach());


        /**
         * 构建请求参数
         */
        Map<String, String> params = build();

        String xmlResult = WxPayApi.pushOrder(false, params);

        log.info("unifiedorder:{}" + xmlResult);

        WxPayAsyncResponse wxPayAsyncResponse = XmlUtil.fromXML(xmlResult, WxPayAsyncResponse.class);


        if ((!WxPaySignature.codeIsOK(wxPayAsyncResponse.getReturnCode())) || (!WxPaySignature.codeIsOK(wxPayAsyncResponse.getResultCode()))) {
            return null;
        }

        // 以下字段在return_code 和result_code都为SUCCESS的时候有返回
        String prepayId = wxPayAsyncResponse.getPrepayId();

        AppPayRequest payResponse = build(prepayId);

        if (payResponse != null) {
            return payResponse;
        }

        return null;

    }


    /**
     * 统一下单构建请求参数
     *
     * @return
     */
    private Map<String, String> build() {

        Map<String, String> map = new HashMap<String, String>();

        if (wxPayApiConfig.getPayModel().equals(PayModel.SERVICEMODE)) {
            map.put("sub_mch_id", wxPayApiConfig.getSubMchId());
            map.put("sub_appid", wxPayApiConfig.getSubAppId());
        }


        map.put("appid", wxPayApiConfig.getAppId());
        map.put("mch_id", wxPayApiConfig.getMchId());
        map.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        map.put("body", wxPayApiConfig.getBody());
        map.put("detail", wxPayApiConfig.getDetail());

        if (StringUtils.isNotBlank(wxPayApiConfig.getAttach())) {
            map.put("attach", wxPayApiConfig.getAttach());
        }

        map.put("out_trade_no", wxPayApiConfig.getOutTradeNo());
        map.put("total_fee", String.valueOf(wxPayApiConfig.getTotalFee()));
        map.put("spbill_create_ip", wxPayApiConfig.getSpbillCreateIp());
        map.put("trade_type", wxPayApiConfig.getTradeType().name());

        if (StringUtils.isNotBlank(wxPayApiConfig.getProductId())) {
            map.put("product_id", wxPayApiConfig.getProductId());
        }

        if (StringUtils.isNotBlank(wxPayApiConfig.getLimitPay())) {
            map.put("limit_pay", wxPayApiConfig.getLimitPay());
        }

        if (StringUtils.isNotBlank(wxPayApiConfig.getOpenId())) {
            map.put("openid", wxPayApiConfig.getOpenId());
        }

        map.put("notify_url", wxPayApiConfig.getNotifyUrl());
        map.put("sign", WxPaySignature.sign(map, wxPayApiConfig.getPaternerSecret()));

        return map;
    }


    /**
     * 查询订单
     *
     * @param payRequest
     * @return
     */
    public WxPayAsyncResponse queryOrder(PayRequest payRequest) {

        Map<String, String> params = orderQueryBuild(payRequest);

        String xmlResult = WxPayApi.orderQuery(false, params);

        log.info("queryOrder:{}" + xmlResult);

        WxPayAsyncResponse wxPayAsyncResponse = XmlUtil.fromXML(xmlResult, WxPayAsyncResponse.class);

        if ((!WxPaySignature.codeIsOK(wxPayAsyncResponse.getReturnCode())) || (!WxPaySignature.codeIsOK(wxPayAsyncResponse.getResultCode()))) {
            return null;
        }

        return wxPayAsyncResponse;

    }

    /**
     * 构建查询订单参数
     *
     * @return <Map<String, String>>
     */
    private Map<String, String> orderQueryBuild(PayRequest payRequest) {
        Map<String, String> map = new HashMap<String, String>();
        if (wxPayApiConfig.getPayModel().equals(PayModel.SERVICEMODE)) {
            map.put("sub_mch_id", wxPayApiConfig.getSubMchId());
            map.put("sub_appid", wxPayApiConfig.getSubAppId());
        }

        if (StringUtils.isNotBlank(payRequest.getTransactionId())) {
            map.put("transaction_id", payRequest.getTransactionId());
        } else {
            if (StringUtils.isBlank(payRequest.getOutTradeNo())) {
                throw new PayIllegalArgumentException("out_trade_no,transaction_id 不能同时为空");
            }
            map.put("out_trade_no", payRequest.getOutTradeNo());
        }

        map.put("appid", wxPayApiConfig.getAppId());
        map.put("mch_id", wxPayApiConfig.getMchId());
        map.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        map.put("sign", WxPaySignature.sign(map, wxPayApiConfig.getPaternerSecret()));
        return map;
    }


    /**
     * APP端调起支付的参考封装(固定格式7个参数)
     * <p>
     * 统一下单接口返回正常的prepay_id，再按签名规范重新生成签名后，将数据传输给APP。
     * 参与签名的字段名为appId，partnerId，prepayId，nonceStr，timeStamp，package。
     * 注意：package的值格式为Sign=WXPay
     * </p>
     *
     * @param prepayId
     * @return
     */
    private AppPayRequest build(String prepayId) {

        if (StringUtils.isNotEmpty(prepayId)) {
            Map<String, String> packageParams = Maps.newHashMap();
            packageParams.put("appid", wxPayApiConfig.getAppId());
            packageParams.put("partnerid", wxPayApiConfig.getMchId());
            packageParams.put("prepayid", prepayId);
            packageParams.put("package", "Sign=WXPay");
            packageParams.put("noncestr", System.currentTimeMillis() + "");
            //10 位固定长度
            packageParams.put("timestamp", String.valueOf(System.currentTimeMillis()).substring(0, 10));
            packageParams.put("sign", WxPaySignature.sign(packageParams, wxPayApiConfig.getPaternerSecret()));
            AppPayRequest appPayRequest = CommonUtils.mapToBean(packageParams, AppPayRequest.class);
            appPayRequest.setPackInfo("Sign=WXPay");
            return appPayRequest;
        }

        return null;
    }


    /**
     * 异步通知参数返回结果
     *
     * @param response
     * @return
     */
    private Map<String, String> buildMap(WxPayAsyncResponse response) {
        Map<String, String> map = new HashMap<>();
        map.put("appid", response.getAppId());
        map.put("mch_id", response.getMchId());
        map.put("sub_mch_id", response.getSubMchId());
        //用自己系统的数据
        map.put("attach", response.getAttach());
        map.put("device_info", response.getDeviceInfo());
        map.put("return_msg", response.getReturnMsg());
        map.put("bank_type", response.getBankType());
        map.put("fee_type", response.getFeeType());
        map.put("is_subscribe", response.getIsSubscribe());
        map.put("nonce_str", response.getNonceStr());
        map.put("openid", response.getOpenid());
        map.put("out_trade_no", response.getOutTradeNo());

        map.put("result_code", response.getResultCode());
        map.put("return_code", response.getReturnCode());
        map.put("cash_fee", response.getCashFee());
        map.put("cash_fee_type", response.getCashFeeType());
        map.put("coupon_fee", response.getCouponFee());
        map.put("coupon_count", response.getCouponCount());

        map.put("err_code", response.getErrCode());
        map.put("err_code_des", response.getErrCodeDes());

        map.put("time_end", response.getTimeEnd());
        //用自己系统的数据：订单金额
        map.put("total_fee", String.valueOf(response.getTotalFee()));
        map.put("trade_type", response.getTradeType());
        map.put("transaction_id", response.getTransactionId());
        //签名
        map.put("sign", response.getSign());
        return map;
    }

    /**
     * notifyData  微信服务端返回的xml格式数据
     * 支付结果通用通知文档: https://pay.weixin.qq.com/wiki/doc/api/jsapi.php?chapter=9_7
     * 特别提醒：商户系统对于支付结果通知的内容一定要做签名验证,并校验返回的订单金额是否与商户侧的订单金额一致，防止数据泄漏导致出现“假通知”，造成资金损失。
     * 当收到通知进行处理时，首先检查对应业务数据的状态，判断该通知是否已经处理过，如果没有处理过再进行处理，如果处理过直接返回结果成功。
     * 在对业务数据进行状态检查和处理之前，要采用数据锁进行幵収控制，以避免凼数重入造成的数据混乱。
     * 判断完成后，我们需要通知微信，我们收到信息了，不然微信就会通过一定的策略定期重新发起通知。
     *
     * @param notifyData
     */
    public PayResponse asyncNotify(String notifyData) {

        log.info("支付通知参数：{}", notifyData);

        //xml解析为对象
        WxPayAsyncResponse asyncResponse = XmlUtil.fromXML(notifyData, WxPayAsyncResponse.class);

        //签名校验
        if (!WxPaySignature.verify(buildMap(asyncResponse), wxPayApiConfig.getPaternerSecret())) {
            log.error("【微信支付异步通知】签名验证失败,微信回调失败,签名可能被篡改 response={}", asyncResponse);
            return null;
        }


        //return_code为SUCCESS的时候
        if (!WxPaySignature.codeIsOK(asyncResponse.getReturnCode())) {
            log.error("【微信支付异步通知】发起支付, 微信回调失败.returnCode != ERROE, returnMsg ={} " + asyncResponse.getReturnMsg());
            return null;
        }

        //获取应用服务器需要的数据进行持久化操作,之后业务数据拿到数据之后通知确认给微信服务端
        if (WxPaySignature.codeIsOK(asyncResponse.getReturnCode())) {
            PayResponse payResponse = new PayResponse();
            // 分转元
            payResponse.setOrderAmount(MoneyUtil.Fen2Yuan(asyncResponse.getTotalFee()));
            payResponse.setOutTradeNo(asyncResponse.getOutTradeNo());
            payResponse.setTransactionId(asyncResponse.getTransactionId());
            return payResponse;
        }

        return null;

    }

    /**
     * 订单处理成功,通知微信
     *
     * @return
     */
    public String asyncNotifySuccess() {

        WxPayAsyncResponse wxPayAsyncResponse = new WxPayAsyncResponse();

        wxPayAsyncResponse.setReturnCode(WxResultCode.SUCCESS.getCode());

        wxPayAsyncResponse.setReturnMsg(WxResultCode.SUCCESS.getMsg());

        return XmlUtil.toXMl(wxPayAsyncResponse);

    }

    /**
     * 订单处理失败
     *
     * @return
     */
    public String asyncNotifyError() {

        WxPayAsyncResponse wxPayAsyncResponse = new WxPayAsyncResponse();

        wxPayAsyncResponse.setReturnCode(WxResultCode.ERROR.getCode());

        wxPayAsyncResponse.setReturnMsg(WxResultCode.ERROR.getMsg());

        return XmlUtil.toXMl(wxPayAsyncResponse);

    }


}
