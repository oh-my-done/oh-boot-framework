package com.gizwits.security.core;

import com.alibaba.fastjson.JSON;
import com.gizwits.common.entity.ApiResponse;
import com.gizwits.common.entity.ErrorData;
import com.gizwits.common.entity.ResponseCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 资源访问受限
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Component
@Slf4j
public class NoAuthenticationEntryPoint implements AuthenticationEntryPoint, AccessDeniedHandler {

    /**
     * 当访问的资源没有权限会调用这里
     *
     * @param request
     * @param response
     * @param authException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
            throws IOException, ServletException {
        handleAccessDenied(request, response);
    }


    private void handleAccessDenied(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        ApiResponse errorInfo = new ApiResponse(ResponseCode.NO_PERMISSON.getCode(), ResponseCode.NO_PERMISSON.getName(), new ErrorData(request.getRequestURI()));
        response.getWriter().println(JSON.toJSONString(errorInfo));
        response.getWriter().flush();
    }

    /**
     * 全局认证处理
     *
     * @param request
     * @param response
     * @param accessDeniedException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        handleAccessDenied(request, response);
    }
}
