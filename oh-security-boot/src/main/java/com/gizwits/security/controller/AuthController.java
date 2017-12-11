package com.gizwits.security.controller;

import com.gizwits.common.annotation.IgnoreAuthenticate;
import com.gizwits.common.entity.ApiResponse;
import com.gizwits.common.entity.ResponseCode;
import com.gizwits.security.core.JpaUserDetailsService;
import com.gizwits.security.jwt.AuthToken;
import com.gizwits.security.jwt.JwtTokenParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@EnableSwagger2
@RestController
@Api(value = "auth", description = "账号安全认证", tags = {"auth"})
@Slf4j
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    @Autowired
    private JwtTokenParser jwtTokenUtil;

    /**
     * @param username
     * @param password
     * @return
     */
    @IgnoreAuthenticate
    @ApiOperation(value = "登入系统账号获取token", notes = "登入系统账号获取token")
    @PostMapping(value = "${jwt.route.authentication.login}")
    public ApiResponse login(@ApiParam(name = "username", required = true, value = "登入账号") @RequestParam(name = "username") String username,
                             @ApiParam(name = "password", required = true, value = "登入账号密码") @RequestParam(name = "password") String password) {


        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);

        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = jpaUserDetailsService.loadUserByUsername(username);

        AuthToken token = jwtTokenUtil.getToken(userDetails);


        if (token != null) {

            return new ApiResponse(ResponseCode.AUTH_SUCCESS.getCode(), ResponseCode.AUTH_SUCCESS.getName(), token);
        }

        return new ApiResponse(ResponseCode.AUTH_ERROR.getCode(), ResponseCode.AUTH_ERROR.getName());
    }


    /**
     * @param token
     * @return
     */
    @IgnoreAuthenticate
    @ApiOperation(value = "刷新新的token", notes = "刷新新的token", authorizations = {@Authorization("${jwt.header}")})
    @PostMapping(value = "${jwt.route.authentication.refresh}")
    public ApiResponse refreshToken(@ApiIgnore @RequestHeader("${jwt.header}") String token) {

        if (StringUtils.isNotEmpty(token)) {
            AuthToken authToken = jwtTokenUtil.refreshNewToken(token);
            if (authToken != null) {
                return new ApiResponse(ResponseCode.AUTH_SUCCESS.getCode(), ResponseCode.AUTH_SUCCESS.getName(), authToken);
            }
        }

        return new ApiResponse(ResponseCode.TOKEN_NOT_EMPTY.getCode(), ResponseCode.TOKEN_NOT_EMPTY.getName());

    }


}
