package com.zlingchun.mybatis.entity.pojo;

import com.zlingchun.mybatis.entity.BaseEntity;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Emp extends BaseEntity {
    private Long eid;

    private String empName;

    private String empNum;

    private String sex;

    private String telNumber;

    private BigDecimal salary;

    private LocalDate birthday;

    private String empAddress;

    private String email;

    private Dep dep;
}