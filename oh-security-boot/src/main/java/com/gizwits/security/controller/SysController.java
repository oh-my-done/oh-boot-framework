package com.gizwits.security.controller;

import com.gizwits.common.entity.ApiResponse;
import com.gizwits.common.entity.ResponseCode;
import com.gizwits.security.jwt.JwtTokenParser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@EnableSwagger2
@RestController
@Api(value = "system", description = "系统管理", tags = {"system"})
@Slf4j
@RequestMapping("/api/v1/sys")
public class SysController {


    @Autowired
    private JwtTokenParser jwtTokenUtil;


    @ApiOperation(value = "获取菜单树", notes = "获取菜单树", authorizations = {@Authorization("${jwt.header}")})
    @GetMapping(value = "/menus")
    public ApiResponse menus() {

        return new ApiResponse(ResponseCode.TOKEN_NOT_EMPTY.getCode(), ResponseCode.TOKEN_NOT_EMPTY.getName());

    }


}
