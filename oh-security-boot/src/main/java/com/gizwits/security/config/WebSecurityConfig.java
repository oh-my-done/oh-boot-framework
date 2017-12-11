package com.gizwits.security.config;


import com.gizwits.common.annotation.IgnoreAuthenticate;
import com.gizwits.common.component.SpringContextHolder;
import com.gizwits.security.core.JwtAuthenticationTokenFilter;
import com.gizwits.security.core.NoAuthenticationEntryPoint;
import com.gizwits.security.core.UsernamePasswordAuthenticationProvider;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Map;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Configuration
@EnableWebSecurity
@EnableJpaRepositories("com.gizwits.security.repository")
@EntityScan("com.gizwits.security.entity")
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UsernamePasswordAuthenticationProvider usernamePasswordAuthenticationProvider;

    @Autowired
    private NoAuthenticationEntryPoint noAuthenticationEntryPoint;

    @Autowired
    private JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * 忽略不需要权限认证的资源路径
     *
     * @return
     */
    private String[] ignorePath() {
        RequestMappingHandlerMapping requestMapping = SpringContextHolder.getBean(RequestMappingHandlerMapping.class);

        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMapping.getHandlerMethods();


        ArrayList<String> ignore = Lists.newArrayList();
        for (RequestMappingInfo key : handlerMethods.keySet()) {

            HandlerMethod handlerMethod = handlerMethods.get(key);
            if (handlerMethod.hasMethodAnnotation(IgnoreAuthenticate.class)) {
                key.getPatternsCondition().getPatterns().stream().forEach(path -> ignore.add(path));

            }
        }
        return ignore.toArray(new String[]{});
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {


        http.csrf().disable()
                .httpBasic().disable()
                .formLogin().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests();

        http.authorizeRequests()
                .antMatchers(ignorePath()).permitAll()
                .antMatchers(
                        HttpMethod.GET,
                        "/",
                        "/*.html",
                        "/favicon.ico",
                        "/*.png",
                        "/**/*.html",
                        "/**/*.css",
                        "/**/*.js",
                        "/v2/api-docs/**",
                        "/druid/**",
                        "/webjars/**",
                        "/swagger-resources/**"
                ).permitAll()
                .anyRequest()
                .authenticated();

        http.addFilterBefore(jwtAuthenticationTokenFilter, UsernamePasswordAuthenticationFilter.class);

        http.exceptionHandling()
                .authenticationEntryPoint(noAuthenticationEntryPoint)
                .accessDeniedHandler(noAuthenticationEntryPoint);


        http.headers().cacheControl();


    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.authenticationProvider(usernamePasswordAuthenticationProvider);
    }

}
