package com.zlingchun.mybatisplus.doman.dto;

import com.zlingchun.mybatisplus.validator.ValidGroup;
import com.zlingchun.mybatisplus.validator.enums.EnumString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author achun
 * @create 2022/7/24
 * @description descrip
 */
@ApiModel(description = "查询员工")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmpQueryDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "员工主键id(16位)")
    private Long id;
    /**
     * 员工名称
     */
    @ApiModelProperty(name = "empName", value = "员工名称", example = "张三", position = 1)
    @Length(message = "员工名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Query.class})
    private String empName;
    /**
     * 员工号
     */
    @ApiModelProperty(name = "empNo", value = "员工编号", position = 2)
    private String empNo;
    /**
     * 手机号
     */
    @ApiModelProperty(name = "phone", value = "手机号", position = 3)
    private String phone;
    /**
     * 薪资
     */
    @ApiModelProperty(name = "salary", value = "薪资", position = 4)
    @Digits(message = "薪资格式不合法", integer = 11, fraction = 3, groups = {ValidGroup.Crud.Query.class})
    private BigDecimal salary;
    /**
     * 性别：0：男；1：女
     */
    @ApiModelProperty(name = "sex", value = "性别：0：男；1：女", example = "男", position = 5)
    @EnumString(message="性别只允许为男或女", value = {"男","女"})
    private String sex;
    /**
     * 年龄
     */
    @ApiModelProperty(name = "age", value = "年龄", example = "18", position = 6)
    @Min(message = "年龄必须大于0", value = 0)
    private Integer age;
    /**
     * 生日日期
     */
    @ApiModelProperty(name = "birth", value = "生日日期", position = 7)
//    ^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$
    @Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$",
            message = "出生日期格式应为：yyyy-MM-dd")
    private String birth; //生日这种需要验证不是未来日期的属性最好设置为LocalDate类型
    /**
     * 地址
     */
    @ApiModelProperty(name = "address", value = "地址", position = 8)
    @Length(message = "最大长度不能超过256个字符", max = 256, groups = {ValidGroup.Crud.Query.class})
    private String address;
    /**
     * 客户邮箱
     */
    @ApiModelProperty(name = "email", value = "邮箱", position = 9)
    private String email;
    /**
     * 部门主键
     */
    @ApiModelProperty(name = "depId", value = "部门主键id(16位)", position = 10)
    private Long depId;
    /**
     * 部门名称
     */
    @ApiModelProperty(name = "depName", value = "部门名称", example = "研发", position = 11)
    @Length(message = "部门名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Query.class})
    private String depName;
    /**
     * 部门编号
     */
    @ApiModelProperty(name = "depNo", value = "部门编号", position = 12)
    private String depNo;
    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "数据状态：0:正常，1:删除", position = 13)
    private Integer status;
    /**
     * 页码，第几页
     */
    @ApiModelProperty(name = "pageNum", value = "页码", example = "1", position = 14)
    @NotNull(message = "分页查询时, 页码不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageNum;
    /**
     * 每页条数
     */
    @ApiModelProperty(name = "pageSize", value = "条数", example = "10", position = 15)
    @NotNull(message = "分页查询时, 条数不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageSize;
}
