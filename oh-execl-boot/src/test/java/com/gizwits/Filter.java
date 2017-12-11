package com.gizwits;

import com.gizwits.utils.excel.ExcelResources;
import lombok.Data;

/**
 * Created by feel on 2017/10/23.
 */
@Data
public class Filter {
    @ExcelResources(title = "滤芯编号")
    private Integer filterNo;
    @ExcelResources(title = "滤芯类型")
    private String filterType;

}
