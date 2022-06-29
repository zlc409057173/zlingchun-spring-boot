package com.zlingchun.mybatis.entity.po;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Dep {
    private Integer did;

    private String depName;

    private List<Emp> emps;
}