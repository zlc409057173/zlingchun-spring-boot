package com.zlingchun.mybatis.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.zlingchun.mybatis.entity.pojo.Dep;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.mapper.EmpMapper;
import com.zlingchun.mybatis.service.DepService;
import com.zlingchun.mybatis.service.EmpService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
@Service
public class EmpServiceImp extends EmpService {

    @Resource
    Snowflake snowflake;

    @Resource
    private EmpMapper empMapper;

    @Resource
    DepService depServiceImp;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveBatch(List<Emp> emps) {
        if(CollectionUtils.isEmpty(emps)){
            return 0;
        }
        emps.forEach(emp -> {
            Dep dep = depServiceImp.exit(emp.getDep());
            emp.setCreateBy("ServiceSaveBatch");
            emp.setUpdateBy("ServiceSaveBatch");
            emp.setUpdateTime(LocalDateTime.now());
            emp.setEid(snowflake.nextId());
            emp.setDep(dep);
        });
        return empMapper.insertBatch(emps);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int save(Emp emp){
        Dep dep = depServiceImp.exit(emp.getDep());
        emp.setCreateBy("ServiceSave");
        emp.setUpdateBy("ServiceSave");
        emp.setUpdateTime(LocalDateTime.now());
        emp.setEid(snowflake.nextId());
        emp.setDep(dep);
        return empMapper.insertSelective(emp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public Emp exit(Emp record){
        Emp emp = this.findPrimary(record);
        if(ObjectUtils.isEmpty(emp)){
            this.save(record);
            return record;
        }
        record.setEid(emp.getEid());
        this.modify(record);
        emp = this.findPrimary(record);
        return emp;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Emp findPrimary(Emp record) {
        return empMapper.selectPrimary(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modify(Emp record) {
        record.setUpdateBy("ServiceModify");
        return empMapper.updateSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int remove(Emp record) {
        return empMapper.deleteSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Emp> findSelective(Emp record) {
        return empMapper.selectSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Emp> selectSelectiveJoinDep(Emp record) {
        return empMapper.selectSelectiveJoinDep(record);
    }
}
