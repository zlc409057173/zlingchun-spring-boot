package com.zlingchun.jpa.service.impl;

import com.zlingchun.jpa.entity.Customer;
import com.zlingchun.jpa.repository.CustomerRepository;
import com.zlingchun.jpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/10
 * @description descrip
 */
@Service
public class ICustomerService implements CustomerService {

    @Autowired
    CustomerRepository customerRepository;

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

}
