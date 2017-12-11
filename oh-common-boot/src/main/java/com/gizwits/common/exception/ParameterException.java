package com.gizwits.common.exception;

/**
 * @author Feel
 * @date 2017/11/13
 * @email fye@gizwits.com
 * @since 1.0
 */
public class ParameterException extends RuntimeException {
    
    public ParameterException() {
        super();
    }

    public ParameterException(String message) {
        super(message);
    }
}
