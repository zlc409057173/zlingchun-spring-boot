package com.zlingchun.mybatis.converter.easyExcel;

import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.zlingchun.mybatis.entity.po.Dep;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
public class SexConverter implements Converter<String> {
    @Override
    public Class<?> supportJavaTypeKey() {
        return Dep.class;
    }
    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        return CellDataTypeEnum.STRING;
    }
    /**
     * 这里读的时候会调用
     *
     * @param context
     * @return
     */
    @Override
    public String convertToJavaData(ReadConverterContext<?> context) {
        String depName = context.getReadCellData().getStringValue();
        if("男".equals(depName)){
            return "0";
        }
        return "1";
    }
    /**
     * 这里是写的时候会调用 不用管
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<String> context) {
        String value = context.getValue();
        return new WriteCellData<>("0".equalsIgnoreCase(value) ? "男":"女");
    }
}
