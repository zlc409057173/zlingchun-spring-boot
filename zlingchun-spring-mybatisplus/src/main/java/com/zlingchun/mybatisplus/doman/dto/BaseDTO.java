package com.zlingchun.mybatisplus.doman.dto;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

/**
 * @author achun
 * @create 2022/7/6
 * @description descrip
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@EqualsAndHashCode
public class BaseDTO {
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
//    @JsonIgnore
    //格式化处理
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ExcelIgnore
    private String createTime;
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
    private String updateTime;
}
