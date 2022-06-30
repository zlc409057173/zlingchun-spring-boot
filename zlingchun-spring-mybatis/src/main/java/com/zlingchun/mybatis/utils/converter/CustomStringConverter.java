package com.zlingchun.mybatis.utils.converter;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.metadata.property.ExcelContentProperty;

import java.io.IOException;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
public class CustomStringConverter implements Converter<String> {
    public CustomStringConverter() {
    }

    public Class<?> supportJavaTypeKey() {
        return String.class;
    }

    public WriteCellData<?> convertToExcelData(String value, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws IOException {
        return new WriteCellData("自定义："+value);
    }
}
