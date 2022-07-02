package com.zlingchun.mybatis.entity.po;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zlingchun.mybatis.converter.DepNameConverter;
import com.zlingchun.mybatis.converter.SexConverter;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
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
    @ExcelProperty(value = "手机号", order = 3)
    private String telNumber;
    @ExcelProperty(value = "薪资", order = 4)
    private BigDecimal salary;
    @ColumnWidth(12)
    @DateTimeFormat(value = "yyyy年MM月dd")
    @ExcelProperty(value = "生日", order = 5)
    private LocalDate birthday;
    @ExcelProperty(value = "家庭住址", order = 6)
    private String empAddress;
    @ExcelProperty(value = "邮箱", order = 7)
    private String email;

    @ExcelProperty(value = "部门", order = 8, converter = DepNameConverter.class)
    private Dep dep;
}