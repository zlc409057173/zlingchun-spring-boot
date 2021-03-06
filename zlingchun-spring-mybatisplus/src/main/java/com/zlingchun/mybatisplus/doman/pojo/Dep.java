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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@TableName(value = "t_dep")
public class Dep implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    @TableId(value = "did", type = IdType.AUTO)
    private Long id;
    /**
     * 部门名称
     */
    private String depName;
    /**
     * 部门编号
     */
    private String depNo;
    /**
     * 状态
     */
//    @TableLogic
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
