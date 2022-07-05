package com.zlingchun.mybatis.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.zlingchun.mybatis.converter.mapper.EmpDtoMapper;
import com.zlingchun.mybatis.entity.dto.EmpDto;
import com.zlingchun.mybatis.entity.pojo.Dep;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.mapper.EmpMapper;
import com.zlingchun.mybatis.service.DepService;
import com.zlingchun.mybatis.service.EmpService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    EmpDtoMapper empDtoMapper;

    @Resource
    DepService depServiceImp;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveBatch(List<EmpDto> empDtos) {

        if(CollectionUtils.isEmpty(empDtos)){
            return 0;
        }
        List<Emp> emps = empDtoMapper.empDto2Emp(empDtos);
        emps.forEach(emp -> {
            Dep dep = depServiceImp.exit(emp.getDep());
            emp.setCreateBy("ServiceSaveBatch");
            emp.setUpdateBy("ServiceSaveBatch");
            emp.setUpdateTime(LocalDateTime.now());
            emp.setEid(snowflake.nextId());
            if(StringUtils.isEmpty(emp.getEmpNum())){
                emp.setEmpNum(String.valueOf(emp.getEid()).substring(8));
            }
            emp.setDep(dep);
        });
        return empMapper.insertBatch(emps);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public int save(EmpDto empDto){
        Emp emp = empDtoMapper.empDto2Emp(empDto);

        Dep dep = depServiceImp.exit(emp.getDep());
        emp.setCreateBy("ServiceSave");
        emp.setUpdateBy("ServiceSave");
        emp.setUpdateTime(LocalDateTime.now());
        emp.setEid(snowflake.nextId());
        if(StringUtils.isEmpty(emp.getEmpNum())){
            emp.setEmpNum(String.valueOf(emp.getEid()).substring(8));
        }
        emp.setDep(dep);
        return empMapper.insertSelective(emp);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public EmpDto exit(EmpDto empDto){
        EmpDto emp = this.findPrimary(empDto);
        if(ObjectUtils.isEmpty(emp)){
            this.save(empDto);
        }else {
            empDto.setEid(emp.getEid());
            this.modify(empDto);
        }
        return this.findPrimary(empDto);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmpDto findPrimary(EmpDto empDto) {
        Emp record = empDtoMapper.empDto2Emp(empDto);
        Emp emp = empMapper.selectPrimary(record);
        return emp == null? null:empDtoMapper.emp2EmpDto(emp);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmpDto findPrimarykey(Long eid) {
        Emp emp = empMapper.selectPrimarykey(eid);
        return emp == null? null:empDtoMapper.emp2EmpDto(emp);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modify(EmpDto empDto) {
        Emp record = empDtoMapper.empDto2Emp(empDto);
        record.setUpdateBy("ServiceModify");
        return empMapper.updateSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int remove(Long eid) {
        EmpDto empDto = empDtoMapper.empDto2Emp(eid);
        Emp record = empDtoMapper.empDto2Emp(empDto);
        return empMapper.deleteSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EmpDto> findSelective(EmpDto empDto) {
        Emp record = empDtoMapper.empDto2Emp(empDto);
        List<Emp> emps = empMapper.selectSelective(record);
        return empDtoMapper.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EmpDto> findSelectiveJoinDep(EmpDto empDto) {
        Emp record = empDtoMapper.empDto2Emp(empDto);
        List<Emp> emps = empMapper.selectSelectiveJoinDep(record);
        return empDtoMapper.emp2EmpDto(emps);
    }
}
