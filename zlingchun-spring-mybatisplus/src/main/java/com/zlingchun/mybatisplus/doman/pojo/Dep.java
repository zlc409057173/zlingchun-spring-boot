package com.zlingchun.mybatisplus.doman.pojo;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
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
@JsonPropertyOrder(value = {"id", "depName", "depNo"})
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
    @JsonIgnore
    private Integer status;
    /**
     * 版本号
     */
    @Version
    @JsonIgnore
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
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
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime updateTime;
}
