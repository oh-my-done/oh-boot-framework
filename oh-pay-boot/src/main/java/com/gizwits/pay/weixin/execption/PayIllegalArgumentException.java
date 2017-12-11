package com.gizwits.pay.weixin.execption;


import com.gizwits.common.exception.ParameterException;

/**
 * 支付参数异常
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
public class PayIllegalArgumentException extends ParameterException {

    public PayIllegalArgumentException() {
        super();
    }

    public PayIllegalArgumentException(String message) {
        super(message);
    }
}
