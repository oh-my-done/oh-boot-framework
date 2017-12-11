package com.gizwits.utils.excel;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


/**
 * 通过该annotation说明某个属性所对应的标题
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ExcelResources {

    /**
     * 属性的标题名称
     *
     * @return
     */
    String title();

}