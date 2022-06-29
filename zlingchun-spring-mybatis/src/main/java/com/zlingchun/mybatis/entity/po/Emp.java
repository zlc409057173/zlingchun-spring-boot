package com.zlingchun.mybatis.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emp {
    private Long eid;

    private String empName;

    private String empNum;

    private String sex;

    private Date birthday;

    private String empAddress;

    private BigDecimal salary;

    private Dep dep;
}