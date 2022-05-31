package com.zlingchun.jpa.hibernate;

import com.zlingchun.jpa.pojo.Customer;
import lombok.extern.jbosslog.JBossLog;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
@JBossLog
public class HibernateTest {

    // Session 工厂-数据库会话
    private SessionFactory sessionFactory;

    @Before
    public void init(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure("/hibernate.cfg.xml").build();
        sessionFactory = new MetadataSources(registry).buildMetadata().buildSessionFactory();
    }

    @Test
    public void testInsert(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        session.save(new Customer("张三", "长沙", "zs"));
        session.save(new Customer("李四", "邵阳", "ls"));
        session.save(new Customer("王五", "娄底", "ww"));
        session.save(new Customer("赵六", "湘潭", "zl"));
        transaction.commit();
        session.close();
    }

    @Test
    public void testUpdate(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        //设置Id表示修改，不设置Id表示新增
        session.saveOrUpdate(Customer.builder().custId(5L).custName("钱七").custAddress("株洲").realName("qq").build());
        transaction.commit();
        session.close();
    }

    @Test
    public void testSelect(){
        Session session = sessionFactory.openSession();
        Customer customer = session.load(Customer.class, 1L);
        log.info("================");
        log.info("customer1 === " + customer);
        // 此时查询会从缓存里面查
        customer = session.find(Customer.class, 1L);
        log.info("================");
        log.info("customer1 === " + customer);
        session.close();
        log.info("========close========");
        log.info("customer1 === " + customer);
        log.info("=============================================");
        //load属于懒加载，只有在使用的时候才会去查询数据库，find表示及时加载，一开始就会查询数据库
        session = sessionFactory.openSession();
        customer = session.find(Customer.class, 2L);
        log.info("================");
        log.info("customer2 === " + customer);
        session.close();
        log.info("========close========");
        log.info("customer2 === " + customer);
        log.info("=============================================");
        session = sessionFactory.openSession();
        customer = session.get(Customer.class, 3L);
        log.info("================");
        log.info("customer3 === " + customer);
        session.close();
        log.info("========close========");
        log.info("customer3 === " + customer);
    }

    @Test
    public void testRemove(){
        Session session = sessionFactory.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = session.load(Customer.class, 5L);
        session.remove(customer);
        transaction.commit();
        session.close();
    }

    @Test
    public void testHql(){
        Session session = sessionFactory.openSession();
        String hql = "From Customer where custName = :name";
        List<Customer> resultList = session.createQuery(hql, Customer.class).setParameter("name", "王五")
                .getResultList();
        log.info("================");
        log.info("resultList : " + resultList.toString());
        session.close();
    }
}
