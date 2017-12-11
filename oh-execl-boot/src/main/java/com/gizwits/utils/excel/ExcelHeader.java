package com.gizwits.utils.excel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来存储Excel标题的对象，通过该对象可以获取标题和字段的对应关系
 *
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExcelHeader {

    /**
     * excel的标题名称
     */
    private String title;

    /**
     * 对应的字段
     */
    private String fieldName;


}