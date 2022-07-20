package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonPropertyOrder(value = {"id","empName","empNo","empPhone","salary","sex","age","birth","address","email","depId","dep","customers","status","version","updateBy"})
public class EmpDto implements Serializable {

    private static final Long serializableId = 1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 员工名称
     */
    private String empName;
    /**
     * 员工号
     */
    private String empNo;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 薪资
     */
    private BigDecimal salary;
    /**
     * 性别：0：男；1：女
     */
    private String sex;
    /**
     * 年龄
     */
    private Integer age;
    /**
     * 生日日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    private String birth;
    /**
     * 生日日期
     */
    private String address;
    /**
     * 客户邮箱
     */
    private String email;
    /**
     * 部门详情
     */
    private DepDto depDto;
    /**
     * 客户列表
     */
    private List<CustomerDto> customerDtos;
    /**
     * 状态
     */
    private Integer status;
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
