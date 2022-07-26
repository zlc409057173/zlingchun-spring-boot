package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@ApiModel("登录用户")
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class UserDto implements Serializable {
    private static final Long serializableId = 1L;
    /**
     * 主键Id
     */
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
    @ApiModelProperty(name = "status", value = "数据状态：0:正常，1:删除", position = 12)
    private Integer status;
    /**
     * 创建人
     */
    @ApiModelProperty(name = "createBy", value = "创建人", position = 13, hidden=true)
    private String createBy;
    /**
     * 创建时间
     */
    @ApiModelProperty(name = "createTime", value = "创建时间", position = 14, hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String createTime;
    /**
     * 更新人
     */
    @ApiModelProperty(name = "updateBy", value = "更新人", position = 15, hidden=true)
    private String updateBy;
    /**
     * 更新时间
     */
    @ApiModelProperty(name = "createTime", value = "更新时间", position = 16, hidden=true)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String updateTime;
}
