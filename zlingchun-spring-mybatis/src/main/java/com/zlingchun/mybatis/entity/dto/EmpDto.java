package com.zlingchun.mybatis.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.zlingchun.mybatis.entity.BaseEntity;
import lombok.*;

import java.math.BigDecimal;

/**
 * @author achun
 * @create 2022/7/3
 * @description descrip
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class EmpDto extends BaseEntity {

    @ExcelIgnore
    private Long eid;

    @ExcelProperty(value = "员工名称", order = 0)
    private String empName;
    @ColumnWidth(11)
    @ExcelProperty(value = "员工编号", order = 1)
    private String empNum;
    @ExcelProperty(value = "性别", order = 2)
    private String sex;
    @ColumnWidth(11)
    @ExcelProperty(value = "手机号", order = 3)
    private String telNumber;
    @ExcelProperty(value = "薪资", order = 4)
    private BigDecimal salary;
    @ColumnWidth(10)
    @ExcelProperty(value = "生日", order = 5)
    private String birthday;
    @ColumnWidth(30)
    @ExcelProperty(value = "家庭住址", order = 6)
    private String address;
    @ColumnWidth(22)
    @ExcelProperty(value = "邮箱", order = 7)
    private String email;

    @ExcelIgnore
    private Integer did;
    @ExcelProperty(value = "部门", order = 8)
    private String depName;
    @ExcelIgnore
    private DepDto depDto;

}