package com.zlingchun.mybatis.mapper;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.po.Dep;
import com.zlingchun.mybatis.entity.po.Emp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.sql.Date;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author achun
 * @create 2022/6/28
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class EmpMapperTest {

    @Resource
    private EmpMapper empMapper;

    @Resource
    private DepMapper depMapper;

    @Resource
    private Snowflake snowflake;

    @Test
//    @Transactional
    void add(){
        Dep dep = Dep.builder()
                .depName("总经办")
                .build();
        depMapper.insertSelective(dep);
        Emp emp = Emp.builder().eid(snowflake.nextId())
                .empName("張三").empNum("99119").sex("1")
                .empAddress("長沙").birthday(Date.from(Instant.now()))
                .salary(BigDecimal.valueOf(100))
                .dep(dep)
                .build();
        empMapper.insertSelective(emp);
    }

    @Test
    void addEmpToDep(){
        Dep dep = depMapper.selectPrimarykey(7);
        Emp emp = Emp.builder().eid(snowflake.nextId())
                .empName("李四").empNum("99118").sex("0")
                .empAddress("湘潭").birthday(Date.from(Instant.now()))
                .salary(BigDecimal.valueOf(800.5))
                .dep(dep)
                .build();
        empMapper.insertSelective(emp);
    }

    @Test
    void addBatchEmp(){
        Dep dep = depMapper.selectPrimarykey(7);
        List<Emp> emps = new ArrayList<>();
        for (int i = 0; i < 2000; i++) {
            Emp emp = Emp.builder().eid(snowflake.nextId())
                    .empName("李四"+i).empNum("99118"+i).sex("0")
                    .empAddress("湘潭").birthday(Date.from(Instant.now()))
                    .salary(BigDecimal.valueOf(800.5+i))
                    .dep(dep)
                    .build();
            emps.add(emp);
        }
        empMapper.batchInsert(emps);
    }

    @Test
    void deleteEmp(){
//        Dep dep = depMapper.selectPrimarykey(7);
//        Dep dep = Dep.builder().depName("经").build();
        Emp emp = Emp.builder().salary(BigDecimal.valueOf(850)).empName("5")
                .build();
        empMapper.deleteSelective(emp);
    }

    @Test
    void deleteBatchEmp(){
        Emp emp = Emp.builder().salary(BigDecimal.valueOf(850)).empName("6")
                .build();
        List<Long> ids = empMapper.selectSelective2(emp).stream().map(Emp::getEid).collect(Collectors.toList());
        empMapper.batchDelete(ids);
    }

    @Test
    void updateEmp(){
        Emp emp = empMapper.selectSelective(Emp.builder().salary(BigDecimal.valueOf(850)).build()).stream().findAny().orElse(null);
        if(emp!=null){
            emp.setBirthday(Date.valueOf(LocalDate.now().minusMonths(2)));
            emp.setEmpName("张三");
            empMapper.updateSelective(emp);
        }
    }

    @Test
    void selectEmp(){
        Emp emp = Emp.builder().dep(Dep.builder().depName("经").build()).build();
        List<Emp> emps = empMapper.selectSelective(emp);
        log.info(JSON.toJSONString(emps));
    }

    @Test
    void selectEmpForSelect(){
        Emp emp = Emp.builder().salary(BigDecimal.valueOf(200)).build();
        List<Emp> emps = empMapper.selectSelective2(emp);
        log.info(JSON.toJSONString(emps));
    }

    @Test
    void selectForeignkey(){
        List<Emp> emps = empMapper.selectForeignkey(7l);
        log.info(JSON.toJSONString(emps));
    }

    @Test
    void selectEmps(){
        Dep dep = depMapper.selectEmps(7);
        log.info(JSON.toJSONString(dep));
    }
}
