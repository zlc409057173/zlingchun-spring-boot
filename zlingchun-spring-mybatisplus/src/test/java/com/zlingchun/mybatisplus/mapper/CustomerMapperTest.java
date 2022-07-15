package com.zlingchun.mybatisplus.mapper;

import com.alibaba.excel.util.ListUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import com.zlingchun.mybatisplus.util.test.RandomInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@Slf4j
@SpringBootTest
public class CustomerMapperTest {

    @Resource
    EmpMapper empMapper;

    @Resource
    CustomerMapper customerMapper;

    private static List<Customer> customers = ListUtils.newArrayList();
    private static final Integer[] sexs = {0, 1};

    @BeforeEach
    @Disabled
    public void baseData() {
        List<Long> empIds = empMapper.selectList(null).stream().distinct().map(Emp::getId).collect(Collectors.toList());
        for (int i = 0; i < 100; i++) {
            LocalDate birthday = RandomInfo.getRandomBirthday();
            customers.add(Customer.builder()
                    .cusName(RandomInfo.getRandomName(null))
                    .cusPhone(RandomInfo.createMobile(RandomInfo.random.nextInt(3)))
                    .sex(sexs[RandomInfo.random.nextInt(sexs.length)])
                    .age(LocalDate.now().getYear()-birthday.getYear())
                    .email(RandomInfo.getRandomQQEmail())
                    .empId(empIds.get(RandomInfo.randomInt(empIds.size())))
                    .build());
        }
    }

    @Test
    void insert(){
        customers.forEach(customer -> {
            LambdaQueryWrapper<Customer> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotEmpty(customer.getCusName()), Customer::getCusName, customer.getCusName());
            if(!customerMapper.exists(queryWrapper)) {
                customer.setCreateBy("mapper");
                customer.setUpdateBy("mapper");
                int insert = customerMapper.insert(customer);
                Assertions.assertEquals(1, insert);
            }
        });
    }

    @Test
    void updateById(){
    }

    @Test
    void update(){
    }

    @Test
    void selectById(){
    }

    @Test
    void selectBatchIds(){
    }

    @Test
    void selectByMap(){
    }

    @Test
    void deleteById(){
    }

    @Test
    void selectPage(){
    }

}
