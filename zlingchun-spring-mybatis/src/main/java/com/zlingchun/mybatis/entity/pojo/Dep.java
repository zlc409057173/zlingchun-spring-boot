package com.zlingchun.mybatis.entity.pojo;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = true)
public class Dep extends BaseEntity {
    private Integer did;

    private String depName;

    private List<Emp> emps;
}