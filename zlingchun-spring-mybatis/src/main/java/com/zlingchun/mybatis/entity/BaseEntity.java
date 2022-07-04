package com.zlingchun.mybatis.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
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
    @ExcelIgnore
    private String createBy;
    /**
     * 创建时间
     */
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
    @ExcelIgnore
    private LocalDateTime updateTime;
}
