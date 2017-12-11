package com.gizwits;

import com.gizwits.utils.excel.ExcelHandle;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;


public class Demo {

    public static void main(String[] args) throws Exception {

        File file = new File("/Users/feel/githome/gizwits/cloud-boot-project/docs/filter.xlsx");

        String path = "/Users/feel/githome/gizwits/cloud-boot-project/docs/filter.xls";

        FileInputStream fileInputStream = new FileInputStream(path);

        List<Filter> filter = ExcelHandle.getInstance().readExcel2JavaBean(path, Filter.class, 0, 0);
        System.out.println(filter);
        List<Filter> filter2 = ExcelHandle.getInstance().readExcel2JavaBean(fileInputStream, Filter.class, 0, 0);

        System.out.println(filter2);
    }
}
