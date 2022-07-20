package com.zlingchun.mybatisplus.service.dto.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.converter.mapstruct.EmpToEmpDto;
import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
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
    EmpToEmpDto empToEmpDto;

    @Override
    public boolean saveBatch(List<EmpDto> empDtos) {
        List<Emp> emps = empToEmpDto.empDto2Emp(empDtos);
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
        EmpDto empOne = this.findOnlyEmpOne(empDto.getId(), empDto.getEmpNo(), empDto.getPhone());
        if(Objects.nonNull(empOne)){
            log.debug("An Emp has the same EmpNo or Phone number, Emp = {}", JSON.toJSONString(empOne));
            throw new IllegalArgumentException("An Emp has the same account or mobile phone number, EmpNo = "+empOne.getEmpNo());
        }
        empDto.setEmpNo(snowflake.nextIdStr().substring(8));
        Emp emp = empToEmpDto.empDto2Emp(empDto);
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
        Emp emp = empToEmpDto.empDto2Emp(empDto);
        List<Emp> emps = empService.selectEmpList(null, emp);
        if(CollectionUtils.isEmpty(emps)){
            throw new IllegalArgumentException("No such Condition's Emp, " + JSON.toJSONString(emp));
        }
        boolean removeBatchCust = true;
        List<Customer> customers = emps.stream().flatMap(emp1 -> emp1.getCustomers().stream()).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(customers)){
            List<CustomerDto> customerDtos = empToEmpDto.customer2CustomerDto(customers);
            removeBatchCust = customerDtoService.remove(customerDtos);
        }
        List<Long> ids = emps.stream().map(Emp::getId).collect(Collectors.toList());
        boolean removeBatchEmp = empService.removeBatchByIds(ids,100);
        return removeBatchCust && removeBatchEmp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(Long id) {
        Emp emp = empService.selectEmpOne(Emp.builder().id(id).build());
        if(Objects.isNull(emp)){
            throw new IllegalArgumentException("The Emp has been deleted, id = " + id);
        }
        List<Customer> customers = emp.getCustomers();
        boolean removeCust = true;
        if(!CollectionUtils.isEmpty(customers)){
            List<CustomerDto> customerDtos = empToEmpDto.customer2CustomerDto(customers);
            removeCust = customerDtoService.remove(customerDtos);
        }
        boolean removeEmp = empService.removeById(emp);
        return removeCust && removeEmp;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean update(Long id, EmpDto empDto) {
        Emp emp = empToEmpDto.empDto2Emp(empDto);
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
    public Page<EmpDto> findEmpPage(EmpDto empDto) {
        Emp emp = empToEmpDto.empDto2Emp(empDto);
        // 分页
        Page<Emp> emps = empService.selectEmpPage(emp, empDto.getPageNum(), empDto.getPageSize());
        return empToEmpDto.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EmpDto> findEmpList(EmpDto empDto) {
        Emp emp = empToEmpDto.empDto2Emp(empDto);
        if (Objects.isNull(emp)) return Collections.emptyList();
        List<Emp> emps = empService.selectEmpList(null, emp);
        return empToEmpDto.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmpDto findEmpOne(Long id, String empNo, String empPhone) {
        Emp empOne = empService.selectEmpOne(Emp.builder().id(id).empNo(empNo).empPhone(empPhone).build());
        return empToEmpDto.emp2EmpDto(empOne);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public EmpDto findOnlyEmpOne(Long id, String empNo, String empPhone) {
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(id), Emp::getId, id)
                .or()
                .eq(StringUtils.isNotBlank(empNo), Emp::getEmpNo, empNo)
                .or()
                .eq(StringUtils.isNotBlank(empPhone), Emp::getEmpPhone, empPhone);
        Emp emp = empService.getOne(lambdaQueryWrapper);
        return empToEmpDto.emp2EmpDto(emp);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<EmpDto> findOnlyEmpList(EmpDto empDto) {
        Emp emp = empToEmpDto.empDto2Emp(empDto);
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = this.getWrapper(emp);
        List<Emp> emps = empService.list(lambdaQueryWrapper);
        return empToEmpDto.emp2EmpDto(emps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<EmpDto> findOnlyEmpPage(EmpDto empDto) {
        Emp emp = empToEmpDto.empDto2Emp(empDto);
        Page<Emp> empPage = new Page<>(empDto.getPageNum(), empDto.getPageSize());
        LambdaQueryWrapper<Emp> lambdaQueryWrapper = this.getWrapper(emp);
        Page<Emp> page = empService.page(empPage, lambdaQueryWrapper);
        return empToEmpDto.emp2EmpDto(page);
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
