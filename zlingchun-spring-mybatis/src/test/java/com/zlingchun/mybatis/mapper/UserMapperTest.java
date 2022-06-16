package com.zlingchun.mybatis.mapper;

import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.po.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;

/**
 * @author achun
 * @create 2022/6/15
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class UserMapperTest {

    @Autowired
    UserMapper userMapper;

    @Test
    public void findAll(){
        log.info(JSON.toJSONString(userMapper.findAll()));
    }

    @Test
    public void insert(){
        User user = User.builder().name("zs").realName("张三").birth(Date.from(Instant.now())).age(30).money(new BigDecimal(10000.345)).build();
        int add = userMapper.add(user);
        log.info("更新了{}条", add);
        log.info("用户信息, {}", JSON.toJSONString(user));
    }

    @Test
    public void insertUser(){
        User user = User.builder().name("ls").realName("李四").birth(Date.from(Instant.now())).age(30).money(new BigDecimal(10000.345)).build();
        int add = userMapper.addUser(user);
        log.info("更新了{}条", add);
        log.info("用户信息, {}", JSON.toJSONString(user));
    }

}
