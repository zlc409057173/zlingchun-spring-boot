package com.zlingchun.mybatis.entity.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zlingchun.mybatis.annotation.EnumString;
import com.zlingchun.mybatis.entity.BaseEntity;
import com.zlingchun.mybatis.validator.ValidGroup;
import lombok.*;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
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
@JsonPropertyOrder(value = {"eid", "depName", "empName", "empNum", "sex", "telNumber", "salary", "birthday", "address", "email", "did"})
public class EmpDto extends BaseEntity {

    @ExcelIgnore
    @NotNull(message = "更新员工Id不能为空", groups = {ValidGroup.Crud.Update.class, ValidGroup.Crud.Query.class})
    private Long eid;

    @ExcelProperty(value = "员工名称", order = 0)
    @NotBlank(message = "新增员工名称不能为空", groups = {ValidGroup.Crud.Create.class})
    private String empName;

    @ColumnWidth(11)
    @ExcelProperty(value = "员工编号", order = 1)
    private String empNum;

    @NotBlank(message = "新增员工性别不能为空", groups = {ValidGroup.Crud.Create.class})
    @EnumString(value = {"男", "女"}, message = "性别只能为男或女")
    @ExcelProperty(value = "性别", order = 2)
    private String sex;

    @ColumnWidth(11)
    @ExcelProperty(value = "手机号", order = 3)
    @Pattern(regexp = "1\\d{10}$", message = "手机号格式错误", groups = {ValidGroup.Crud.Update.class, ValidGroup.Crud.Query.class})
    private String telNumber;

    @ExcelProperty(value = "薪资", order = 4)
    @Min(value = 0, message = "薪资不能小于0")
    @Digits(integer = 11, fraction = 3, message = "薪资整数精确位为11, 小数精确位为3, 只能为整数或小数")
    private BigDecimal salary;

    @ColumnWidth(10)
    @ExcelProperty(value = "生日", order = 5)
    @Past(message = "生日日期必须是过去", groups = {ValidGroup.Crud.Update.class, ValidGroup.Crud.Query.class})
    private String birthday;

    @ColumnWidth(30)
    @ExcelProperty(value = "家庭住址", order = 6)
    @Length(max = 128, message = "地址长度最大不能超过128位", groups = {ValidGroup.Crud.Update.class, ValidGroup.Crud.Query.class})
    private String address;

    @ColumnWidth(22)
    @ExcelProperty(value = "邮箱", order = 7)
    @Email(message = "邮箱格式不合法")
    private String email;

    @ExcelIgnore
    private Integer did;
    @ExcelProperty(value = "部门", order = 8)
    @NotBlank(message = "新增员工员工部门不能为空", groups = {ValidGroup.Crud.Create.class})
    private String depName;
}
