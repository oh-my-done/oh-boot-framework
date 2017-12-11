package com.gizwits.swagger2;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author Feel
 * @date 2017/12/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SwaggerAutoConfiguration.class})
public @interface EnableSwagge2Doc {
}
