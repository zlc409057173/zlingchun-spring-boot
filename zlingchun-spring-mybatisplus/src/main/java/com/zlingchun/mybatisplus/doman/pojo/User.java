package com.zlingchun.mybatisplus.doman.pojo;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@TableName("t_user")
public class User {
    /**
     * 主键Id
     */
    @TableId("uid")
    private Long id;
    /**
     * 用户编码
     */
    private String userCode;
    /**
     * 用户名称
     */
    private String userName;
    /**
     * 密码
     */
    private String password;
    /**
     * 角色Id
     */
    private String roleId;

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
