package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zlingchun.mybatisplus.validator.ValidGroup;
import com.zlingchun.mybatisplus.validator.enums.EnumString;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(value = {"id","empName","empNo","empPhone","salary","sex","age","birth","address","email","depId","dep","customers","status","version","updateBy"})
public class EmpDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 员工名称
     */
    @NotNull(message = "新增时员工名称不能为空", groups = {ValidGroup.Crud.Create.class})
    @Length(message = "员工名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String empName;
    /**
     * 员工号
     */
    @Null(message = "员工号必须为空", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String empNo;
    /**
     * 手机号
     */
    @NotNull(message = "新增员工时, 手机号不能为空", groups = {ValidGroup.Crud.Create.class})
    @Pattern(message = "手机号不合法", regexp = "^((13[0-9])|(14[0,1,4-9])|(15[0-3,5-9])|(16[2,5,6,7])|(17[0-8])|(18[0-9])|(19[0-3,5-9]))\\d{8}$", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String phone;
    /**
     * 薪资
     */
    @Digits(message = "薪资格式不合法", integer = 11, fraction = 3, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private BigDecimal salary;
    /**
     * 性别：0：男；1：女
     */
    @NotNull(message = "新增员工时, 性别不能为空", groups = {ValidGroup.Crud.Create.class})
    @EnumString(message="性别只允许为男或女", value = {"男","女"})
    private String sex;
    /**
     * 年龄
     */
    @Min(message = "年龄必须大于0", value = 0)
    private Integer age;
    /**
     * 生日日期
     */
//    ^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))\\s+([0-1]?[0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$
    @Pattern(regexp = "^((([0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3})-(((0[13578]|1[02])-(0[1-9]|[12][0-9]|3[01]))|((0[469]|11)-(0[1-9]|[12][0-9]|30))|(02-(0[1-9]|[1][0-9]|2[0-8]))))|((([0-9]{2})(0[48]|[2468][048]|[13579][26])|((0[48]|[2468][048]|[3579][26])00))-02-29))$",
            message = "出生日期格式应为：yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birth; //生日这种需要验证不是未来日期的属性最好设置为LocalDate类型
    /**
     * 地址
     */
    @Length(message = "最大长度不能超过256个字符", max = 256, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String address;
    /**
     * 客户邮箱
     */
    @Email(message = "邮箱格式不合法", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String email;
    /**
     * 部门详情
     */
    @NotNull(message = "新增员工时, 部门信息不能为空", groups = {ValidGroup.Crud.Create.class})
    private DepDto depDto;
    /**
     * 客户列表
     */
    private List<CustomerDto> customerDtos;
    /**
     * 状态
     */
    private Integer status;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
    /**
     * 页码，第几页
     */
    @NotNull(message = "分页查询时, 页码不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageNum;
    /**
     * 每页条数
     */
    @NotNull(message = "分页查询时, 条数不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageSize;
}
