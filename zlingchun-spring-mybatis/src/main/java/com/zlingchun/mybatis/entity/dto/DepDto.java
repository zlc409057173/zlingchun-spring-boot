package com.zlingchun.mybatis.entity.dto;

import com.zlingchun.mybatis.entity.BaseEntity;
import com.zlingchun.mybatis.entity.pojo.Emp;
import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class DepDto extends BaseEntity {
    private Integer did;

    private String name;

    private List<Emp> emps;
}