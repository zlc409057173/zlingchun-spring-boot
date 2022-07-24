package com.zlingchun.mybatisplus.doman.dto;

import com.zlingchun.mybatisplus.validator.ValidGroup;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@ApiModel(description = "查询部门")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepQueryDto implements Serializable {

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
    @Length(message = "部门名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Query.class})
    private String depName;
    /**
     * 部门编号
     */
    @ApiModelProperty(name = "depNo", value = "部门编号", position = 2)
    private String depNo;
    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "数据状态：0:正常，1:删除", position = 3)
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
    private String updateTime;
    /**
     * 页码，第几页
     */
    @ApiModelProperty(name = "pageNum", value = "页码", example = "1", position = 8)
    @NotNull(message = "分页查询时, 页码不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageNum;
    /**
     * 每页条数
     */
    @ApiModelProperty(name = "pageSize", value = "条数", example = "10", position = 9)
    @NotNull(message = "分页查询时, 条数不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageSize;
}
