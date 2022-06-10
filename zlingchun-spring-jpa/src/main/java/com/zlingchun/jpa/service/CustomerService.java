package com.zlingchun.jpa.service;

import com.zlingchun.jpa.entity.Customer;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/10
 * @description descrip
 */
public interface CustomerService {

    List<Customer> getAll();
}
