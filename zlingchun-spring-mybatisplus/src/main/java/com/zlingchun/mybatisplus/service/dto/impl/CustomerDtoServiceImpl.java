package com.zlingchun.mybatisplus.service.dto.impl;

import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlingchun.mybatisplus.converter.mapstruct.CustomerConvert;
import com.zlingchun.mybatisplus.doman.dto.CustomerDto;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import com.zlingchun.mybatisplus.service.dto.ICustomerDtoService;
import com.zlingchun.mybatisplus.service.pojo.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
@Slf4j
@Service
public class CustomerDtoServiceImpl implements ICustomerDtoService {

    @Resource
    ICustomerService customerService;

    @Resource
    CustomerConvert customerConvert;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean save(List<CustomerDto> customerDtos) {
        List<Customer> customers = customerConvert.customerDto2Customer(customerDtos);
        return customerService.saveOrUpdateBatch(customers,100);
    }

    /**
     * 根据员工Id删除客户
     * @param empIds
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean removeByEmpId(List<Long> empIds) {
        LambdaQueryWrapper<Customer> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.in(Customer::getEmpId, empIds);
        List<Customer> customers = customerService.list(lambdaQueryWrapper);
        if(CollectionUtils.isEmpty(customers)){
            log.info("These customers are in the deleted state, empId in ({})", JSON.toJSONString(empIds));
            return true;
        }
        return customerService.remove(lambdaQueryWrapper);
    }
}
