package com.zlingchun.mybatis.entity.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zlingchun.mybatis.converter.DepNameConverter;
import com.zlingchun.mybatis.converter.SexConverter;
import com.zlingchun.mybatis.entity.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emp extends BaseEntity {
    @ExcelIgnore
    private Long eid;

    @ExcelProperty(value = "员工名称", order = 0)
    private String empName;
    @ColumnWidth(12)
    @ExcelProperty(value = "员工编号", order = 1)
    private String empNum;
    @ExcelProperty(value = "性别", order = 2, converter = SexConverter.class)
    private String sex;
    @ColumnWidth(12)
    @DateTimeFormat(value = "yyyy年MM月dd")
    @ExcelProperty(value = "生日", order = 3)
    private Date birthday;
    @ExcelProperty(value = "家庭住址", order = 5)
    private String empAddress;
    @ExcelProperty(value = "薪资", order = 4)
    private BigDecimal salary;
    @ExcelProperty(value = "部门", order = 6, converter = DepNameConverter.class)
    private Dep dep;
}