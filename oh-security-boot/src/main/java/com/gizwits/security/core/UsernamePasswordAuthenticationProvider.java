package com.gizwits.security.core;

import com.gizwits.common.entity.ResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * 自定义用户名密码认证管理器。
 * 参考类实现DaoAuthenticationProvider
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Component
public final class UsernamePasswordAuthenticationProvider implements AuthenticationProvider {


    @Autowired
    private JpaUserDetailsService jpaUserDetailsService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String name = authentication.getName();
        String password = authentication.getCredentials().toString();

        UserDetails details = jpaUserDetailsService.loadUserByUsername(name);

        if (null != details) {
            String encodePassword = DigestUtils.md5DigestAsHex((password).getBytes());
            if (details.getPassword().equalsIgnoreCase(encodePassword)) {
                Authentication auth = new UsernamePasswordAuthenticationToken(name, "", details.getAuthorities());
                return auth;
            } else {
                throw new BadCredentialsException(ResponseCode.LOGIN_PASSWORD_ERROR.getName());
            }
        } else {
            throw new UsernameNotFoundException(ResponseCode.LOGIN_USERNAME_ERROR.getName());
        }
    }

    /**
     * 判断是否使用UsernamePasswordAuthenticationToken作为认证服务
     *
     * @param authentication
     * @return
     */
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }


}