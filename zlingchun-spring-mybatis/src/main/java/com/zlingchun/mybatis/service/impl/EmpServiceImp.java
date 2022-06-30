package com.zlingchun.mybatis.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.github.pagehelper.PageHelper;
import com.zlingchun.mybatis.entity.po.Dep;
import com.zlingchun.mybatis.entity.po.Emp;
import com.zlingchun.mybatis.mapper.DepMapper;
import com.zlingchun.mybatis.mapper.EmpMapper;
import com.zlingchun.mybatis.service.EmpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

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
    Snowflake snowflake;
    @Resource
    private EmpMapper empMapper;

    @Resource
    private DepMapper depMapper;

    @Override
    public List<Emp> findEmps(Emp emp) {
        return empMapper.selectSelective(emp);
    }

    @Override
    public List<Emp> findEmps(Emp emp, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return empMapper.selectSelective(emp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int batchSave(List<Emp> emps) {
        if(CollectionUtils.isEmpty(emps)){
            return 0;
        }
        emps.forEach(emp -> {
            Dep depCondition = emp.getDep();
            Dep dep = depMapper.selectSelective(depCondition);
            if(ObjectUtils.isEmpty(dep)){
                depMapper.insertSelective(depCondition);
                dep = depCondition;
            }
            emp.setEid(snowflake.nextId());
            emp.setDep(dep);
        });
        return empMapper.batchInsert(emps);
    }
}
