package com.zlingchun.mybatisplus.service.dto.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.converter.mapstruct.EmpConvert;
import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;
import com.zlingchun.mybatisplus.doman.dto.EmpQueryDto;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import com.zlingchun.mybatisplus.service.dto.ICustomerDtoService;
import com.zlingchun.mybatisplus.service.dto.IDepDtoService;
import com.zlingchun.mybatisplus.service.dto.IEmpDtoService;
import com.zlingchun.mybatisplus.service.pojo.IEmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
@Service
@Slf4j
public class EmpDtoServiceImpl extends IEmpDtoService {

    @Resource
    IEmpService empService;

    @Resource
    IDepDtoService depDtoService;

    @Resource
    ICustomerDtoService customerDtoService;

    @Resource
    Snowflake snowflake;

    @Resource
    EmpConvert empConvert;

    @Override
    public boolean saveBatch(List<EmpDto> empDtos) {
        List<Emp> emps = empConvert.empDto2Emp(empDtos);
        return empService.saveBatch(emps, 100);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean save(EmpDto empDto) {
        // 获取部门信息：
        //   1.如果部门不存在就新增，新增需确保DepName不为空，并设置DepNo，返回自动生成的Id
        //   2.如果部门存在就获取部门Id，不做任何改变
        DepDto depDto = empDto.getDepDto();
        if(Objects.isNull(depDto)){
            throw new IllegalArgumentException("The Dep information cannot be empty while create the Emp");
        }
        boolean saveDep = depDtoService.save(depDto);
        // 获取员工信息，判断是否存在相同信息的员工
        //   1. 如果已经存在，那么就不能新增
        //   2. 不存在就新增，并设置EmpNo
        if(Objects.nonNull(empDto.getId()) || StringUtils.isNotEmpty(empDto.getEmpNo()) || StringUtils.isNotEmpty(empDto.getPhone())){
            EmpQueryDto empQueryDto = empConvert.empDto2EmpQueryDto(empDto);
            EmpDto empOne = this.findOnlyEmpOne(empQueryDto);
            if(Objects.nonNull(empOne)){
                log.debug("An Emp has the same EmpNo or Phone number, Emp = {}", JSON.toJSONString(empOne));
                throw new IllegalArgumentException("An Emp has the same account or mobile phone number, EmpNo = "+empOne.getEmpNo());
            }
        }
        empDto.setEmpNo(snowflake.nextIdStr().substring(8));
        Emp emp = empConvert.empDto2Emp(empDto);
        emp.setDepId(depDto.getId()); //设置员工的部门Id
        // 新增员工信息
        boolean saveEmp = empService.save(emp);

        // 获取客户信息
        List<CustomerDto> customerDtos = empDto.getCustomerDtos();
        if(CollectionUtils.isEmpty(customerDtos)){
            return saveDep && saveEmp;
        }
        customerDtos.parallelStream().forEach(customerDto -> {
            customerDto.setEmpId(emp.getId());
            customerDto.setId(snowflake.nextId()); // 批量插入调用自定义的mapper 接口，需要使用雪花算法生成id
        });
        boolean saveOrUpdateCust = customerDtoService.save(customerDtos);
        return saveDep && saveEmp && saveOrUpdateCust;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(EmpDto empDto) {
        EmpQueryDto empQueryDto = empConvert.empDto2EmpQueryDto(empDto);
        List<EmpDto> empDtos = this.findOnlyEmpList(empQueryDto);
        if(CollectionUtils.isEmpty(empDtos)){
            throw new IllegalArgumentException("No such Condition's Emp, " + JSON.toJSONString(empDto));
        }
        List<Long> ids = empDtos.stream().map(EmpDto::getId).collect(Collectors.toList());
        customerDtoService.removeByEmpId(ids);
        return empService.removeBatchByIds(ids,100);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(Long id) {
        EmpDto empDto = this.findOnlyEmpOne(EmpQueryDto.builder().id(id).build());
        if(Objects.isNull(empDto)){
            throw new IllegalArgumentException("The Emp has been deleted, id = " + id);
        }
        customerDtoService.removeByEmpId(Arrays.asList(id));
        return empService.removeById(id);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean removeByDepId(List<Long> depIds) {
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(CollectionUtils.isEmpty(depIds), Emp::getDepId, depIds);
        List<Emp> emps = empService.list(lambdaQueryWrapper);
        if(CollectionUtils.isEmpty(emps)){
            log.info("These emps are in the deleted state, depId in ({})", JSON.toJSONString(depIds));
            return true;
        }
        List<Long> empIds = emps.stream().map(Emp::getId).collect(Collectors.toList());
        boolean removeCusts = customerDtoService.removeByEmpId(empIds);
        boolean removeEmps = empService.removeBatchByIds(empIds, 100);
        return removeCusts && removeEmps;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean update(Long id, EmpDto empDto) {
        Emp emp = empConvert.empDto2Emp(empDto);
        if(StringUtils.isNotBlank(emp.getEmpNo()) || StringUtils.isNotBlank(emp.getEmpPhone())){
            LambdaQueryWrapper<Emp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
            lambdaQueryWrapper.eq(StringUtils.isNotBlank(emp.getEmpNo()), Emp::getEmpNo, emp.getEmpNo()).or()
                    .eq(StringUtils.isNotBlank(emp.getEmpPhone()), Emp::getEmpPhone, emp.getEmpPhone());
            long count = empService.count(lambdaQueryWrapper);
            if(count > 0){
                throw new IllegalArgumentException("The telephone num has exist, " + JSON.toJSONString(empDto));
            }
        }

        LambdaQueryWrapper<Emp> lambdaUpdateWrapper = new LambdaQueryWrapper<>();
        lambdaUpdateWrapper.eq(Emp::getId, id);
        return empService.update(emp, lambdaUpdateWrapper);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<EmpDto> findEmpPage(EmpQueryDto empQueryDto) {
        Emp emp = empConvert.empQueryDto2Emp(empQueryDto);
        // 分页
        Page<Emp> emps = empService.selectEmpPage(emp, empQueryDto.getPageNum(), empQueryDto.getPageSize());
        return empConvert.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EmpDto> findEmpList(EmpQueryDto empQueryDto) {
        Emp emp = empConvert.empQueryDto2Emp(empQueryDto);
        if (Objects.isNull(emp)) return Collections.emptyList();
        List<Emp> emps = empService.selectEmpList(null, emp);
        return empConvert.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmpDto findEmpOne(EmpQueryDto empQueryDto) {
        Emp emp = empConvert.empQueryDto2Emp(empQueryDto);
        Emp empOne = empService.selectEmpOne(emp);
        return empConvert.emp2EmpDto(empOne);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmpDto findOnlyEmpOne(EmpQueryDto empQueryDto) {
        Emp emp = empConvert.empQueryDto2Emp(empQueryDto);
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(emp.getId()), Emp::getId, emp.getId())
                .or()
                .eq(StringUtils.isNotBlank(emp.getEmpNo()), Emp::getEmpNo, emp.getEmpNo())
                .or()
                .eq(StringUtils.isNotBlank(emp.getEmpPhone()), Emp::getEmpPhone, emp.getEmpPhone());
        Emp one = empService.getOne(lambdaQueryWrapper);
        return empConvert.emp2EmpDto(one);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EmpDto> findOnlyEmpList(EmpQueryDto empQueryDto) {
        Emp emp = empConvert.empQueryDto2Emp(empQueryDto);
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = this.getWrapper(emp);
        List<Emp> emps = empService.list(lambdaQueryWrapper);
        return empConvert.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<EmpDto> findOnlyEmpPage(EmpQueryDto empQueryDto) {
        Emp emp = empConvert.empQueryDto2Emp(empQueryDto);
        Page<Emp> empPage = new Page<>(empQueryDto.getPageNum(), empQueryDto.getPageSize());
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = this.getWrapper(emp);
        Page<Emp> page = empService.page(empPage, lambdaQueryWrapper);
        return empConvert.emp2EmpDto(page);
    }

    private LambdaQueryWrapper<Emp> getWrapper(Emp emp){
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(emp.getId()), Emp::getId, emp.getId())
                .eq(StringUtils.isNotBlank(emp.getEmpNo()), Emp::getEmpNo, emp.getEmpNo())
                .eq(StringUtils.isNotBlank(emp.getEmpPhone()), Emp::getEmpPhone, emp.getEmpPhone())
                .like(StringUtils.isNotBlank(emp.getEmpName()), Emp::getEmpName, emp.getEmpName())
                .eq(Objects.nonNull(emp.getAge()), Emp::getAge, emp.getAge())
                .like(StringUtils.isNotBlank(emp.getAddress()), Emp::getAddress, emp.getAddress());
        return lambdaQueryWrapper;
    }
}
