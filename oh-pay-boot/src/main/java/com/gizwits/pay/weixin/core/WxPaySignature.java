package com.gizwits.pay.weixin.core;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public class WxPaySignature {


    /**
     * 构建短链接参数
     *
     * @param appid
     * @param sub_appid
     * @param mch_id
     * @param sub_mch_id
     * @param short_url
     * @param paternerKey
     * @return <Map<String, String>>
     */
    public static Map<String, String> buildShortUrlParasMap(String appid, String sub_appid, String mch_id, String sub_mch_id, String short_url, String paternerKey) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("appid", appid);
        params.put("sub_appid", sub_appid);
        params.put("mch_id", mch_id);
        params.put("sub_mch_id", sub_mch_id);
        params.put("short_url", short_url);

        return buildSignAfterParasMap(params, paternerKey);

    }

    /**
     * 构建签名之后的参数
     *
     * @param params
     * @param paternerKey
     * @return <Map<String, String>>
     */
    public static Map<String, String> buildSignAfterParasMap(Map<String, String> params, String paternerKey) {
        params.put("nonce_str", String.valueOf(System.currentTimeMillis()));
        String sign = sign(params, paternerKey);
        params.put("sign", sign);
        return params;
    }

    /**
     * 签名
     *
     * @param params
     * @param partnerKey
     * @return
     */
    public static String sign(Map<String, String> params, String partnerKey) {
        SortedMap<String, String> sortedMap = new TreeMap<>(params);

        StringBuilder toSign = new StringBuilder();
        for (String key : sortedMap.keySet()) {
            String value = params.get(key);
            if (StringUtils.isNotEmpty(value) && !"sign".equals(key) && !"key".equals(key)) {
                toSign.append(key).append("=").append(value).append("&");
            }
        }

        toSign.append("key=").append(partnerKey);
        return DigestUtils.md5Hex(toSign.toString()).toUpperCase();
    }

    public static String toXml(Map<String, String> params) {
        StringBuilder xml = new StringBuilder();
        xml.append("<xml>");
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            // 略过空值
            if (StringUtils.isBlank(value))
                continue;
            xml.append("<").append(key).append(">");
            xml.append(entry.getValue());
            xml.append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return xml.toString();
    }

    /**
     * 校验签名
     *
     * @param params
     * @param signKey
     * @return
     */
    public static Boolean verify(Map<String, String> params, String signKey) {
        String sign = sign(params, signKey);
        return sign.equals(params.get("sign"));
    }

    /**
     * 判断接口返回的code是否是SUCCESS
     *
     * @param return_code
     * @return {boolean}
     */
    public static boolean codeIsOK(String return_code) {
        return StringUtils.isNotBlank(return_code) && "SUCCESS".equals(return_code);
    }

    /**
     * 替换url中的参数
     *
     * @param str
     * @param regex
     * @param args
     * @return {String}
     */
    public static String replace(String str, String regex, String... args) {
        int length = args.length;
        for (int i = 0; i < length; i++) {
            str = str.replaceFirst(regex, args[i]);
        }
        return str;
    }


}
