package com.gizwits.common.annotation;

import java.lang.annotation.*;

/**
 * 权限认证忽略的路径注解
 *
 * @author Feel
 * @date 2017/11/30
 * @email fye@gizwits.com
 * @since 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface IgnoreAuthenticate {

}
