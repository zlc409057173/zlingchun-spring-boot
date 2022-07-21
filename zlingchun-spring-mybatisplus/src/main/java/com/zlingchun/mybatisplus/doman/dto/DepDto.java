package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.zlingchun.mybatisplus.validator.ValidGroup;
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
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {"id", "depName", "depNo"})
public class DepDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 部门名称
     */
    @NotNull(message = "部门名称不能为空", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    @Length(message = "部门名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String depName;
    /**
     * 部门编号
     */
    @Null(message = "部门编号必须为空", groups = {ValidGroup.Crud.Create.class, ValidGroup.Crud.Update.class})
    private String depNo;
    /**
     * 状态
     */
    @JsonIgnore
    private Integer status;
    /**
     * 版本号
     */
    @JsonIgnore
    private Integer version;
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
