package com.zlingchun.mybatis.entity.dto.esayExcel.read;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
@Data
@EqualsAndHashCode
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReaderData {
    @ExcelIgnore
    private Long id;
    //    在使用ExcelProperty注解的使用，如果想不空列则需要加入order字段，而不是index,order会忽略空列，然后继续往后，而index，不会忽略空列，在第几列就是第几列。
    @ExcelProperty(value = "商品", order = Integer.MAX_VALUE-2)
    private String title;
    @ExcelProperty(value = "生产日期")
    private LocalDateTime proDate;
    @ExcelProperty(value = "价格",order = Integer.MAX_VALUE-1)
    private BigDecimal price;
}
