package com.gizwits.common.controller;

import com.gizwits.common.annotation.IgnoreAuthenticate;
import com.gizwits.common.entity.ApiResponse;
import com.gizwits.common.entity.Profile;
import com.gizwits.common.entity.ResponseCode;
import com.gizwits.common.entity.SysStatusCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统信息
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@RestController
@Api(value = "system", description = "系统信息", tags = {"system"})
@RequestMapping("/api/v1/sys")
public class SystemController {


    @Value("${app.version}")
    private String version;

    @GetMapping("/error/code")
    @IgnoreAuthenticate
    @ApiOperation(value = "错误码列表", notes = "错误码列表")
    public ApiResponse<List<SysStatusCode>> getErrors() {

        List<SysStatusCode> errors = Arrays.asList(ResponseCode.values()).stream()
                .map(value -> new SysStatusCode(value.getCode(), value.getName())).collect(Collectors.toList());

        return ApiResponse.success(errors);
    }

    @GetMapping("/status/version")
    @IgnoreAuthenticate
    @ApiOperation(value = "版本号", notes = "版本号")
    public ApiResponse<Profile> getVersions() {

        return ApiResponse.success((version == null) ? new Profile("1.0") : new Profile(version));
    }
}
