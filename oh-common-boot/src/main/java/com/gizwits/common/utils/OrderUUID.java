package com.gizwits.common.utils;

import org.apache.commons.lang3.StringUtils;

/**
 * @author Feel
 * @date 2017/11/7
 * @email fye@gizwits.com
 * @since 1.0
 */
public class OrderUUID {


    /**
     * 订单号生成算法
     *
     * @return
     */
    public static String generate() {

        return String.valueOf((long) ((Math.random()+1) * 2017L)) + StringUtils.reverse(String.valueOf(System.nanoTime())).substring(0, 8) + String.valueOf((long) ((Math.random()+1) * 2017L));
    }

}
