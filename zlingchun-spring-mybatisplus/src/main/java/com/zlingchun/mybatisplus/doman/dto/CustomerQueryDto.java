package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.zlingchun.mybatisplus.validator.ValidGroup;
import com.zlingchun.mybatisplus.validator.enums.EnumString;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@ApiModel(description = "客户")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomerQueryDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    @ApiModelProperty(name = "id", value = "客户主键id(16位)")
    private Long id;
    /**
     * 客户名称
     */
    @ApiModelProperty(name = "cusName", value = "客户名称", position = 1)
    @Length(message = "员工名称长度必须在2到10之间", min = 2, max = 10, groups = {ValidGroup.Crud.Query.class})
    private String cusName;
    /**
     * 客户手机号
     */
    @ApiModelProperty(name = "cusPhone", value = "手机号", example = "133****7132", position = 2)
    private String cusPhone;
    /**
     * 客户性别：0：男；1：女
     */
    @ApiModelProperty(name = "sex", value = "性别：0：男；1：女", example = "男", position = 3)
    @EnumString(message="性别只允许为男或女", value = {"男","女"})
    private String sex;
    /**
     * 客户年龄
     */
    @ApiModelProperty(name = "age", value = "年龄", example = "18", position = 4)
    @Min(message = "年龄必须大于0", value = 0)
    private Integer age;
    /**
     * 客户邮箱
     */
    @ApiModelProperty(name = "email", value = "客户邮箱", example = "***@qq.com", position = 5)
    private String email;
    /**
     * 外键， 员工id
     */
    @ApiModelProperty(name = "empId", value = "外键， 员工id", position = 6)
    private Long empId;
    /**
     * 状态
     */
    @ApiModelProperty(name = "status", value = "数据状态：0:正常，1:删除", example = "0", position = 7)
    private Integer status;
    /**
     * 创建人
     */
    @ApiModelProperty(name = "createBy", value = "创建人", position = 8, hidden=true)
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime", value = "创建时间", position = 9, hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(name = "updateBy", value = "更新人", position = 10, hidden=true)
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(name = "createTime", value = "更新时间", position = 11, hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
    /**
     * 页码，第几页
     */
    @ApiModelProperty(name = "pageNum", value = "页码", example = "1", position = 12)
    @NotNull(message = "分页查询时, 页码不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageNum;
    /**
     * 每页条数
     */
    @ApiModelProperty(name = "pageSize", value = "条数", example = "10", position = 13)
    @NotNull(message = "分页查询时, 条数不能为空", groups = {ValidGroup.Crud.Query.class})
    private Integer pageSize;
}
