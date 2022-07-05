package com.zlingchun.mybatis.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
@Data
public class BaseEntity {
    /**
     * 当前页数
     */
    @ExcelIgnore
    private Integer pageNum;
    /**
     * 条数
     */
    @ExcelIgnore
    private Integer pageSize;
    /**
     * 创建人
     */
    @JsonIgnore
    @ExcelIgnore
    private String createBy;
    /**
     * 创建时间
     */
//    @JsonIgnore
    //格式化处理
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    @ExcelIgnore
    private String updateBy;
    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private LocalDateTime updateTime;
}
