package com.gizwits.security.jwt;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@ApiModel(description = "token")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthToken implements Serializable {
    @ApiModelProperty(value = "有效token")
    @JsonProperty("access_token")
    private String accessToken;
    @ApiModelProperty(value = "过期时长(单位毫秒)")
    @JsonProperty("expires_in")
    private Long expiresIn;

}
