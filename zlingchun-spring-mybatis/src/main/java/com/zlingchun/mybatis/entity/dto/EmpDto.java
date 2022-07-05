package com.zlingchun.mybatis.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zlingchun.mybatis.annotation.EnumString;
import com.zlingchun.mybatis.entity.BaseEntity;
import com.zlingchun.mybatis.validator.ValidGroup;
import lombok.*;

import javax.validation.constraints.NotBlank;
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
@JsonIgnoreProperties(value = {"updateBy", "updateTime"})
public class EmpDto extends BaseEntity {

    @ExcelIgnore
    @NotBlank(message = "员工Id不能为空", groups = {ValidGroup.Crud.Update.class})
    private Long eid;

    @ExcelProperty(value = "员工名称", order = 0)
    @NotBlank(message = "员工名称不能为空", groups = {ValidGroup.Crud.Create.class})
    private String empName;

    @ColumnWidth(11)
    @ExcelProperty(value = "员工编号", order = 1)
    private String empNum;

    @NotBlank(message = "性别不能为空", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    @EnumString(value = {"男", "女"}, message = "性别只能为男或女")
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
}
