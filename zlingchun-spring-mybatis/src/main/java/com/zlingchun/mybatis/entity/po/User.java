package com.zlingchun.mybatis.entity.po;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author achun
 * @create 2022/6/15
 * @description descrip
 */
@Data
@Builder
public class User {
    private Integer id;
    private String name;
    private String realName;
    private Date birth;
    private Integer age;
    private BigDecimal money;
}