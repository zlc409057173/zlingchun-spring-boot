package com.zlingchun.jpa;

import com.zlingchun.jpa.config.SpringDataJPAConfig;
import com.zlingchun.jpa.pojo.Account;
import com.zlingchun.jpa.pojo.Customer;
import com.zlingchun.jpa.repositories.AccountRepository;
import com.zlingchun.jpa.repositories.CustomerRepository;
import lombok.extern.jbosslog.JBossLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author achun
 * @create 2022/6/1
 * @description descrip
 */
@JBossLog
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class OneToOneTest {

    @Autowired
    CustomerRepository repository;

    @Autowired
    AccountRepository accountRepository;

    @Test
    public void testInserts(){
        Account account = Account.builder().password("123456").username("zs").build();
        Customer customer = Customer.builder().custName("张三").custAddress("长沙").account(account).build();
        account.setCustomer(customer);
        repository.save(customer);
    }

    @Test
    public void testRemove(){
        Customer customer = Customer.builder().custId(1L).custName("李四").account(null).build();
        repository.save(customer);
    }

}
