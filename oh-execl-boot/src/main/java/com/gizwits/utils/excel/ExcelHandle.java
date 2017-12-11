package com.gizwits.utils.excel;

import com.gizwits.common.entity.ResponseCode;
import com.gizwits.common.exception.InvalidException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Feel
 * @date 2017/11/1
 * @email fye@gizwits.com
 * @since 1.0
 */
@Slf4j
public class ExcelHandle {

    private static ExcelHandle excelHandle = new ExcelHandle();

    private ExcelHandle() {
    }

    public static ExcelHandle getInstance() {
        return excelHandle;
    }

    /**
     * 从类路径读取相应的Excel文件到对象列表，标题行为0，没有尾行
     *
     * @param path 路径
     * @param clz  类型
     * @return 对象列表
     */
    public <T> List<T> readExcel2JavaBean(String path, Class<T> clz) {
        return this.readExcel2JavaBean(path, clz, 0, 0);
    }

    public <T> List<T> readExcel2JavaBean(InputStream inputStream, Class<T> clz) {
        return this.readExcel2JavaBean(inputStream, clz, 0, 0);
    }

    /**
     * 从文件路径读取相应的Excel文件到对象列表
     *
     * @param path     文件路径下的path
     * @param clz      对象类型
     * @param readLine 开始行，注意是标题所在行
     * @param tailLine 底部有多少行，在读入对象时，会减去这些行
     * @return
     */
    public <T> List<T> readExcel2JavaBean(String path, Class<T> clz, int readLine, int tailLine) {
        Workbook wb = null;
        try {
            wb = WorkbookFactory.create(new File(path));
            return readExcel2JavaBean(wb, clz, readLine, tailLine);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> List<T> readExcel2JavaBean(InputStream inputStream, Class<T> clz, int readLine, int tailLine) {
        Workbook wb = null;
        try {

            wb = WorkbookFactory.create(inputStream);
            return readExcel2JavaBean(wb, clz, readLine, tailLine);
        } catch (InvalidFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private <T> List<T> readExcel2JavaBean(Workbook wb, Class<T> classOfT, int readLine, int tailLine) {


        Sheet sheet = wb.getSheetAt(0);
        List<T> data = new ArrayList<>();
        try {
            Row row = sheet.getRow(readLine);
            Map<Integer, String> headerMap = getHeaderMap(row, classOfT);

            if (headerMap == null || headerMap.size() <= 0) {
                throw new InvalidException(ResponseCode.INVALID_EMPTY.getName());
            }
            for (int i = readLine + 1; i <= sheet.getLastRowNum() - tailLine; i++) {
                row = sheet.getRow(i);
                T obj = classOfT.newInstance();
                for (Cell c : row) {

                    int columnIndex = c.getColumnIndex();
                    if (headerMap.containsKey(columnIndex)) {
                        String javaObjectFieldName = headerMap.get(columnIndex);
                        BeanUtils.copyProperty(obj, javaObjectFieldName, getCellValue(c));
                    }
                }
                data.add(obj);
            }

        } catch (Exception e) {

            throw new InvalidException(ResponseCode.INVALID_ERROR.getName());
        }

        return data;
    }


    private List<ExcelHeader> getHeaderList(Class clz) {
        List<ExcelHeader> headers = new ArrayList<ExcelHeader>();
        Field[] fields = clz.getDeclaredFields();
        for (Field f : fields) {
            if (f.isAnnotationPresent(ExcelResources.class)) {
                ExcelResources er = f.getAnnotation(ExcelResources.class);
                headers.add(new ExcelHeader(er.title(), f.getName()));
            }
        }
        return headers;
    }

    private Map<Integer, String> getHeaderMap(Row titleRow, Class clz) {
        List<ExcelHeader> headers = getHeaderList(clz);
        Map<Integer, String> maps = new HashMap<Integer, String>();
        for (Cell c : titleRow) {
            String title = c.getStringCellValue();
            for (ExcelHeader eh : headers) {
                if (eh.getTitle().equals(title.trim())) {
                    maps.put(c.getColumnIndex(), eh.getFieldName());
                    break;
                }
            }
        }
        return maps;
    }

    private Object getCellValue(Cell c) {

        Object o = null;
        switch (c.getCellTypeEnum()) {
            case BLANK:

                break;
            case BOOLEAN:
                o = c.getBooleanCellValue();
                break;
            case FORMULA:
                o = c.getCellFormula();
                break;
            case NUMERIC:
                o = c.getNumericCellValue();
                break;
            case STRING:
                o = c.getStringCellValue().trim();
                break;
            default:
                o = c.getStringCellValue().trim();
                break;
        }
        return o;
    }
}