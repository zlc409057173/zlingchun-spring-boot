package com.zlingchun.mybatisplus.doman.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("t_customer")
public class Customer implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    @TableId(value = "cid")
    private Long id;
    /**
     * 客户名称
     */
    private String cusName;
    /**
     * 客户手机号
     */
    private String cusPhone;
    /**
     * 客户性别：0：男；1：女
     */
    private Integer sex;
    /**
     * 客户年龄
     */
    private Integer age;
    /**
     * 客户邮箱
     */
    private String email;
    /**
     * 外键， 员工id
     */
    private Long empId;
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
    @TableField(fill = FieldFill.INSERT, value = "createBy")
    private String createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT, value = "createTime")
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE, value = "updateBy")
    private String updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE, value = "updateTime")
    private LocalDateTime updateTime;
}
