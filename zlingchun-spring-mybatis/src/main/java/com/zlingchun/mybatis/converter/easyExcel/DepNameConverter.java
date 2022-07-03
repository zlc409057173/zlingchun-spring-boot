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
public class DepNameConverter implements Converter<Dep> {
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
    public Dep convertToJavaData(ReadConverterContext<?> context) {
        String depName = context.getReadCellData().getStringValue();
        return Dep.builder().depName(depName).build();
    }
    /**
     * 这里是写的时候会调用 不用管
     *
     * @return
     */
    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Dep> context) {
        Dep dep = context.getValue();
        return new WriteCellData<>(dep.getDepName());
    }
}
