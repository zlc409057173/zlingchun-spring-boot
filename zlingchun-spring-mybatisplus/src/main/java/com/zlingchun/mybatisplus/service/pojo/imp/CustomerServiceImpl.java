package com.zlingchun.mybatisplus.service.pojo.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import com.zlingchun.mybatisplus.mapper.CustomerMapper;
import com.zlingchun.mybatisplus.service.pojo.ICustomerService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {

    @Resource
    CustomerMapper customerMapper;

//    @Override
//    public boolean saveBatch(List<Customer> customers) {
//        return customerMapper.insertBatch(customers);
//    }
}
