package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(value = JsonInclude.Include.NON_NULL)
@JsonPropertyOrder(value = {"id","cusName","cusPhone","sex","age","email","empId","status","version"})
public class CustomerDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
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
    private Integer status;
    /**
     * 版本号
     */
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
    private Integer pageNum;
    /**
     * 每页条数
     */
    private Integer pageSize;
}
