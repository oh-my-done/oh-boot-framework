package com.gizwits.common.handel;

import com.gizwits.common.entity.ApiResponse;
import com.gizwits.common.entity.ErrorData;
import com.gizwits.common.entity.ResponseCode;
import com.gizwits.common.exception.ParameterException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.http.HttpServletRequest;
import java.sql.SQLException;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@EnableWebMvc
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ApiResponse jsonErrorHandler(HttpServletRequest req, Exception e) throws Exception {


        ApiResponse apiResponse = new ApiResponse<ErrorData>(ResponseCode.NO_PERMISSON.getCode(), ResponseCode.NO_PERMISSON.getName(), new ErrorData(req.getRequestURI()));

        if (e instanceof NoHandlerFoundException) {
            apiResponse.setCode(ResponseCode.API_NO_FOUND.getCode());
            apiResponse.setMessage(ResponseCode.API_NO_FOUND.getName());
        } else if (e instanceof MethodArgumentTypeMismatchException) {
            apiResponse.setCode(ResponseCode.INVALID_ERROR.getCode());
            apiResponse.setMessage(ResponseCode.INVALID_ERROR.getName());
            log.error("MethodArgumentTypeMismatchException:{}", e);
        } else if (e instanceof SQLException) {
            apiResponse.setCode(ResponseCode.SQLEXCEPTION.getCode());
            apiResponse.setMessage(ResponseCode.SQLEXCEPTION.getName());
            log.error("SQLException:{}", e);
        } else if (e instanceof ParameterException) {
            apiResponse.setCode(ResponseCode.EXCEPTION.getCode());
            apiResponse.setMessage(e.getMessage());
            log.error("ParameterException:{}", e);
        } else if (e instanceof MethodArgumentNotValidException) {
            apiResponse.setCode(ResponseCode.NOTVALIDEXCEPTION.getCode());
            apiResponse.setMessage(((MethodArgumentNotValidException) e).getBindingResult().getFieldError().getDefaultMessage());
        } else {
            ResponseCode responseCode = ResponseCode.getResponseCode(e.getMessage());
            if (responseCode != null) {
                apiResponse.setCode(responseCode.getCode());
                apiResponse.setMessage(responseCode.getName());
            }
        }

        return apiResponse;

    }
}
