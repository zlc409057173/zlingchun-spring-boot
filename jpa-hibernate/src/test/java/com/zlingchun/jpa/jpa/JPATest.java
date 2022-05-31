package com.zlingchun.jpa.jpa;

import com.zlingchun.jpa.pojo.Customer;
import lombok.extern.jbosslog.JBossLog;
import org.junit.Before;
import org.junit.Test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
@JBossLog
public class JPATest {
    EntityManagerFactory hibernateJPA;

    @Before
    public void init(){
//        hibernateJPA = Persistence.createEntityManagerFactory("hibernateJPA");
        hibernateJPA = Persistence.createEntityManagerFactory("openJPA");
    }

    @Test
    public void testInsert(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.persist(new Customer("曾嘉义", "深圳", "zjy"));
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testUpdate(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        entityManager.merge(new Customer(1L, "曾宝怡", "深圳", "zjy"));
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testUpdateJPQL(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String jpql= "update Customer set custName=?1 where custId=:id";
        entityManager.createQuery(jpql).setParameter(1, "曾宝义").setParameter("id", 2L).executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testSelect(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        entityManager.find(Customer.class, 1L);
        entityManager.close();
    }

    @Test
    public void testSelectJPQL(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        String jpql = "FROM Customer where custName = :name";
        List<Customer> customers = entityManager.createQuery(jpql, Customer.class).setParameter("name", "曾宝怡").getResultList();
        log.info(customers);
        entityManager.close();
    }

    @Test
    public void testSelectNative(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        String sql = "select * from cst_customer where cust_name=:name";
        List<Customer> customers = entityManager.createNativeQuery(sql, Customer.class).setParameter("name", "曾宝怡").getResultList();
        log.info(customers);
        entityManager.close();
    }

    @Test
    public void testRemove(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        String hql = "from Customer where custName = ?1";
        Customer customer = entityManager.createQuery(hql, Customer.class).setParameter(1, "曾宝怡").getSingleResult();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        // 此处需要先查询才能删除
        entityManager.remove(customer);
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testRemoveJPQL(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        EntityTransaction transaction = entityManager.getTransaction();
        transaction.begin();
        String hql = "delete from Customer where custName = ?1";
        entityManager.createQuery(hql).setParameter(1, "曾宝义").executeUpdate();
        transaction.commit();
        entityManager.close();
    }

    @Test
    public void testHql(){
        EntityManager entityManager = hibernateJPA.createEntityManager();
        String hql = "from Customer where custName = ?1";
        Customer customer = entityManager.createQuery(hql, Customer.class).setParameter(1, "曾宝怡").getSingleResult();
        log.info(customer);
        entityManager.close();
    }
}
