package com.zlingchun.mybatis.service;

import com.zlingchun.mybatis.entity.po.Emp;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
public interface EmpService {

    List<Emp> findEmps(String empName);
}
