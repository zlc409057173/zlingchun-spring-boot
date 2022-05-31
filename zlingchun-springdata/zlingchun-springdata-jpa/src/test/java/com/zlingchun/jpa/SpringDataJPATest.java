package com.zlingchun.jpa;

import com.zlingchun.jpa.config.SpringDataJPAConfig;
import com.zlingchun.jpa.pojo.Customer;
import com.zlingchun.jpa.repositories.CustomerRepository;
import lombok.extern.jbosslog.JBossLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
@JBossLog
//@ContextConfiguration(locations = "classpath:/spring.xml")
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringDataJPATest {

    @Autowired
    CustomerRepository dao;

    @Test
    public void testSelect(){
        Customer customer = dao.findById(3L).orElse(Customer.builder().build());
        log.info(customer.toString());
    }

    @Test
    public void testInserts(){
        dao.saveAll(() -> {
            List<Customer> list = new ArrayList<>();
            list.add(new Customer("张三", "长沙", "zs"));
            list.add(new Customer("李四", "邵阳", "ls"));
            list.add(new Customer("王五", "娄底", "ww"));
            list.add(new Customer("赵六", "湘潭", "zl"));
            return list.iterator();
        });
    }

    @Test
    public void testInsert(){
        dao.save(new Customer("钱七", "株洲", "qq"));
    }

    @Test
    public void testUpdate(){
        dao.save(new Customer(13L,"张三", "株洲", "zs"));
    }

    @Test
    public void testDelete(){
        dao.deleteAll();
    }

    @Test
    public void testPage(){
        Sort.TypedSort<Customer> typedSort = Sort.sort(Customer.class);
        Sort sort = typedSort.by(Customer::getCustName).ascending().and(typedSort.by(Customer::getCustId).descending());
        Iterable<Customer> customers = dao.findAll(sort);
        customers.forEach(customer -> log.info(customer.getCustId()));

        Page<Customer> customerPage = dao.findAll(PageRequest.of(0, 1));
        log.info(customerPage.getSize());
        log.info(customerPage.getContent().toString());
        log.info(customerPage.getTotalPages());
    }

    @Test
    public void testJPQL(){
        Customer custName = dao.findCustomerByCustName("张三");
        log.info(custName);
        List<Customer> customers = dao.findCustomerByCustAddress("株洲");
        log.info(customers.toString());
        dao.updateCustAddressByCustName("长沙", "张三");
    }

    @Test
    public void testJPQL1(){
        log.info(dao.getCustomerByCustNameAndCustAddress("张三", "长沙"));
    }

    @Test
    public void testExist(){
        log.info(dao.existsCustomersByCustAddress("长沙"));
    }

    @Test
    public  void testExampleMatcher(){
        Customer customer = Customer.builder().custName("李").custAddress("长沙").build();
        //创建匹配器，即如何使用查询条件
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withMatcher("custName", ExampleMatcher.GenericPropertyMatchers.startsWith()) //姓名采用“开始匹配”的方式查询
                .withIgnorePaths("custAddress");  //忽略属性：是否关注。因为是基本类型，需要忽略掉
        //创建实例
        Example<Customer> ex = Example.of(customer, matcher);
        List<Customer> customers = dao.findAll(ex);
        log.info(customers);
    }
}
