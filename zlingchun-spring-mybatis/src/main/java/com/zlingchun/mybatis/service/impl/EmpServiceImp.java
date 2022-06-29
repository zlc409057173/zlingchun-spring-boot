package com.zlingchun.mybatis.service.impl;

import com.zlingchun.mybatis.entity.po.Emp;
import com.zlingchun.mybatis.mapper.DepMapper;
import com.zlingchun.mybatis.mapper.EmpMapper;
import com.zlingchun.mybatis.service.EmpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
@Service
public class EmpServiceImp implements EmpService {

    @Resource
    private EmpMapper empMapper;

    @Resource
    private DepMapper depMapper;

    @Override
    public List<Emp> findEmps(String empName) {
        Emp emp = Emp.builder().empName(empName).build();
        return empMapper.selectSelective(emp);
    }
}
