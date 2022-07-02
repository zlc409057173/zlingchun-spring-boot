package com.zlingchun.mybatis.entity.po;

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
    private Integer pageNum;
    /**
     * 条数
     */
    private Integer pageSize;
    /**
     * 创建人
     */
    private String createBy;
    /**
     * 创建时间
     */
    private LocalDateTime createTime;
    /**
     * 更新人
     */
    private String updateBy;
    /**
     * 更新时间
     */
    private LocalDateTime updateTime;
}
