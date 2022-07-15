package com.zlingchun.mybatisplus.mapper;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import com.zlingchun.mybatisplus.util.test.RandomInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
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
public class EmpMapperTest {

    @Resource
    EmpMapper empMapper;

    @Resource
    DepMapper depMapper;

    private static final BigDecimal[] salary = {BigDecimal.valueOf(8000.01), BigDecimal.valueOf(10000.5), BigDecimal.valueOf(3000)};
    private static final Integer[] sexs = {0, 1};
    private static List<Emp> emps = ListUtils.newArrayList();

    @BeforeEach
    void baseData() {
        Snowflake snowflake = new Snowflake(1, 1);
        List<Long> depIds = depMapper.selectList(null).stream().distinct().map(Dep::getId).collect(Collectors.toList());
        for (int i = 0; i < 100; i++) {
            LocalDate birthday = RandomInfo.getRandomBirthday();
            emps.add(Emp.builder()
                    .empName(RandomInfo.getRandomName(null))
                    .empNo(snowflake.nextIdStr().substring(8))
                    .empPhone(RandomInfo.createMobile(RandomInfo.random.nextInt(3)))
                    .salary(salary[RandomInfo.random.nextInt(salary.length)].multiply(BigDecimal.valueOf(RandomInfo.random.nextFloat())).setScale(2, RoundingMode.HALF_UP))
                    .sex(sexs[RandomInfo.random.nextInt(sexs.length)])
                    .age(LocalDate.now().getYear()-birthday.getYear())
                    .birth(birthday)
                    .email(RandomInfo.getRandomQQEmail())
                    .address(RandomInfo.getRandomAddress())
                    .depId(depIds.get(RandomInfo.randomInt(depIds.size())))
                    .build());
        }
    }

    @Test
    void insert(){
        emps.forEach(emp -> {
            LambdaQueryWrapper<Emp> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Emp::getEmpNo, emp.getEmpNo()).or().eq(Emp::getEmpPhone, emp.getEmpPhone());
            log.info(JSON.toJSONString(emp));
            if(!empMapper.exists(queryWrapper)) {
                emp.setCreateBy("mapper");
                emp.setUpdateBy("mapper");
                int insert = empMapper.insert(emp);
                Assertions.assertEquals(1, insert);
            }
        });
    }

    @Test
    void update(){
        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("emp_name", "国克");
        Emp emp = emps.get(0);
        log.info(JSON.toJSONString(emp));
        emp.setUpdateBy("mapper");
        int update = empMapper.update(emp, queryWrapper);
        Assertions.assertEquals(1, update);
    }

    @Test
    void updateById(){
        Emp emp = empMapper.selectById(1547593783404625922L);
        log.info(JSON.toJSONString(emp));
        emp.setEmpName("国克");
        int update = empMapper.updateById(emp);
        Assertions.assertEquals(1, update);
    }

    @Test
    void delete(){
        QueryWrapper<Emp> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("emp_name", "向");
        int delete = empMapper.delete(queryWrapper);
        Assertions.assertEquals(1, delete);
    }
}
