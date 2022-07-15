package com.zlingchun.mybatisplus.doman.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "t_emp")
public class Emp implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    @TableId(value = "eid", type = IdType.ASSIGN_ID)
    private Long id;
    /**
     * 员工名称
     */
    private String empName;
    /**
     * 员工号
     */
    private String empNo;
    /**
     * 手机号
     */
    private String empPhone;
    /**
     * 薪资
     */
    private BigDecimal salary;
    /**
     * 性别：0：男；1：女
     */
    private Integer sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 生日日期
     */
    private LocalDate birth;
    /**
     * 生日日期
     */
    private String address;
    /**
     * 客户邮箱
     */
    private String email;
    /**
     * 外键， 部门id
     */
    private Long depId;
    /**
     * 状态
     */
    @TableLogic
    private Integer status;
    /**
     * 版本号
     */
    @Version
    private Integer version;
    /**
     * 创建人
     */
    @TableField(value = "createBy")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "createTime")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @TableField(value = "updateBy")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE, value = "updateTime")
    private LocalDateTime updateTime;
}
