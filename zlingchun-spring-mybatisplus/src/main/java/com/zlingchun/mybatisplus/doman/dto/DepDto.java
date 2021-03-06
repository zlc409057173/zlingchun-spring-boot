package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zlingchun.mybatisplus.validator.ValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.io.Serializable;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@ApiModel(description = "部门")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {"id", "depName", "depNo", "status", "updateBy", "updateTime", "createBy", "createTime"})
public class DepDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "部门主键id(16位)")
    private Long id;
    /**
     * 部门名称
     */
    @ApiModelProperty(name = "depName", value = "部门名称", example = "研发", position = 1)
    @NotNull(message = "部门名称不能为空", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    @Length(message = "部门名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String depName;
    /**
     * 部门编号
     */
    @ApiModelProperty(name = "depNo", value = "部门编号", position = 2)
    @Null(message = "部门编号必须为空", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String depNo;
    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "数据状态：0:正常，1:删除", position = 3)
    @JsonIgnore
    private Integer status;
    /**
     * 创建人
     */
    @ApiModelProperty(name = "createBy", value = "创建人", position = 4, hidden=true)
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime", value = "创建时间", position = 5, hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(name = "updateBy", value = "更新人", position = 6, hidden=true)
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(name = "updateTime", value = "更新时间", position = 7, hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
}
