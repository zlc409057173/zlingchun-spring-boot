package com.zlingchun.mybatisplus.doman.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String depName;
    /**
     * 部门编号
     */
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
    private Integer pageNum;
    /**
     * 每页条数
     */
    private Integer pageSize;
}
