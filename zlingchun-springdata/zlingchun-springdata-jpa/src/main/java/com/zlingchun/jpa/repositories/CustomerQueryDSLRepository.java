package com.zlingchun.jpa.repositories;

import com.zlingchun.jpa.pojo.Customer;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
public interface CustomerQueryDSLRepository extends PagingAndSortingRepository<Customer, Long>, QuerydslPredicateExecutor<Customer> {
}
