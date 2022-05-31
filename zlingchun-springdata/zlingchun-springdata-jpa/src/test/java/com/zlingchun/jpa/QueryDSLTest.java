package com.zlingchun.jpa;

import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zlingchun.jpa.config.SpringDataJPAConfig;
import com.zlingchun.jpa.pojo.QCustomer;
import com.zlingchun.jpa.repositories.CustomerQueryDSLRepository;
import lombok.extern.jbosslog.JBossLog;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * @author achun
 * @create 2022/5/31
 * @description descrip
 */
@JBossLog
//@ContextConfiguration(locations = "classpath:/spring.xml")
@ContextConfiguration(classes = SpringDataJPAConfig.class)
@RunWith(SpringJUnit4ClassRunner.class)
public class QueryDSLTest {
    @Autowired
    CustomerQueryDSLRepository repository;

    @PersistenceContext
    EntityManager em;

    @Test
    public void testQueryDSL(){
        Predicate predicate = QCustomer.customer.custName.endsWithIgnoreCase("三")
                .and(QCustomer.customer.custAddress.startsWithIgnoreCase("长"));
        log.info(repository.findAll(predicate));
    }

    @Test
    public void testNativeSQL(){
        JPAQueryFactory factory = new JPAQueryFactory(em);
        QCustomer customer = QCustomer.customer;
        JPAQuery<Tuple> jpaQuery = factory.select(customer.custName, customer.custAddress).from(customer)
                .where(customer.custId.eq(13L)).orderBy(customer.custId.desc())
                .groupBy(customer.custName, customer.custAddress);
        jpaQuery.fetch().forEach(tuple -> log.info(tuple.get(customer.custName)));
    }

}
