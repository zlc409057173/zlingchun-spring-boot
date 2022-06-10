package com.zlingchun.jpa.repository;

import com.zlingchun.jpa.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author achun
 * @create 2022/6/10
 * @description descrip
 */
public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
