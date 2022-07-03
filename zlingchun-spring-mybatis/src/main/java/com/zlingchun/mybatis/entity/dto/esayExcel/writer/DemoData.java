package com.zlingchun.mybatis.entity.dto.esayExcel.writer;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

import java.util.Date;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DemoData {
//    在使用ExcelProperty注解的使用，如果想不空列则需要加入order字段，而不是index,order会忽略空列，然后继续往后，而index，不会忽略空列，在第几列就是第几列。
    @ExcelProperty(value = "字符串标题", order = Integer.MAX_VALUE-2)
    private String string;
    @ExcelProperty(value = "日期标题", order = Integer.MAX_VALUE-1)
    private Date date;
    @ExcelProperty(value = "数字标题")
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
    private String ignore;
}