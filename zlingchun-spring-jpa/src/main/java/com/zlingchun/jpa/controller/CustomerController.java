package com.zlingchun.jpa.controller;

import com.zlingchun.jpa.entity.Customer;
import com.zlingchun.jpa.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/10
 * @description descrip
 */
@RestController
@RequestMapping("/cust")
public class CustomerController {
    @Autowired
    CustomerService customerService;

    @GetMapping()
    public List<Customer> customers(){
        return customerService.getAll();
    }

}
