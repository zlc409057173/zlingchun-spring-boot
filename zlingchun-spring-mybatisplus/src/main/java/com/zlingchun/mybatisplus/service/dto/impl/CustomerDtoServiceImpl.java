package com.zlingchun.mybatisplus.service.dto.impl;

import com.zlingchun.mybatisplus.converter.mapstruct.EmpToEmpDto;
import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import com.zlingchun.mybatisplus.service.dto.ICustomerDtoService;
import com.zlingchun.mybatisplus.service.pojo.ICustomerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
@Service
public class CustomerDtoServiceImpl implements ICustomerDtoService {

    @Resource
    ICustomerService customerService;

    @Resource
    EmpToEmpDto empToEmpDto;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean save(List<CustomerDto> customerDtos) {
        List<Customer> customers = empToEmpDto.customerDto2Customer(customerDtos);
        return customerService.saveOrUpdateBatch(customers,100);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean saveBatch(List<CustomerDto> customerDtos) {
        List<Customer> customers = empToEmpDto.customerDto2Customer(customerDtos);
        return customerService.saveBatch(customers, 100);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(List<CustomerDto> customersDtos) {
        return customerService.removeBatchByIds(customersDtos.parallelStream().map(CustomerDto::getId).collect(Collectors.toList()));
    }
}
