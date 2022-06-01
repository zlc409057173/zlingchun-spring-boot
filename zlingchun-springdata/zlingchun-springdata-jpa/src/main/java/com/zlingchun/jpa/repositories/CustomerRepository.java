package com.zlingchun.jpa.repositories;

import com.zlingchun.jpa.pojo.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
//public interface CustomerRepository extends CrudRepository<Customer, Long> {
//public interface CustomerRepository extends PagingAndSortingRepository<Customer, Long> {
public interface CustomerRepository extends JpaRepository<Customer, Long>, JpaSpecificationExecutor<Customer> {

    @Query("from Customer where custName = :name")
    Customer findCustomerByCustName(@Param("name") String name);

    @Query("select c from Customer c where c.custAddress = ?1 ")
    List<Customer> findCustomerByCustAddress(String address);

    @Transactional
    @Modifying
    @Query("update Customer set custAddress = :address where custName = :name")
    int updateCustAddressByCustName(@Param("address") String address, @Param("name") String name);

    Customer getCustomerByCustNameAndCustAddress(String custName, String custAddress);

    boolean existsCustomersByCustAddress(String custAddress);
}
