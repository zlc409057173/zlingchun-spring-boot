package com.zlingchun.mybatis.mapper;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.pojo.Dep;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.utils.RandomInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;
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

    private static final BigDecimal[] salary = {BigDecimal.valueOf(8000.01), BigDecimal.valueOf(10000.5), BigDecimal.valueOf(3000)};
    private static final String[] sexs = {"0", "1"};
    private static final String[] depNames = {"总经办", "研发", "测试", "产品"};
    private static List<Emp> emps = ListUtils.newArrayList();

//    @BeforeEach
    void baseData() {
        Random random = new Random();
        Snowflake snowflake = new Snowflake(1, 1);
        for (int i = 0; i < 100; i++) {
            emps.add(Emp.builder()
                    .empName(RandomInfo.getRandomName(null))
                    .empNum(snowflake.nextIdStr().substring(8))
                    .telNumber(RandomInfo.createMobile(random.nextInt(3)))
                    .email(RandomInfo.getRandomQQEmail())
                    .birthday(RandomInfo.getRandomBirthday())
                    .salary(salary[random.nextInt(salary.length)].multiply(BigDecimal.valueOf(random.nextFloat())).setScale(2, RoundingMode.HALF_UP))
                    .sex(sexs[random.nextInt(sexs.length)])
                    .empAddress(RandomInfo.getRandomAddress())
                    .dep(depMapper.selectPrimary(Dep.builder().depName(depNames[RandomInfo.randomInt(depNames.length)]).build()))
                    .build());
        }
    }

    @Test
    void insert(){
        Emp emp = emps.get(0);
        emp.setCreateBy("mapper");
        emp.setUpdateBy("mapper");
        emp.setEid(snowflake.nextId());
        int insertSelective = empMapper.insertSelective(emp);
        Assertions.assertEquals(1, insertSelective);
    }

    @Test
    void insertBatch(){
        emps.forEach(emp -> {
            emp.setCreateBy("mapper");
            emp.setUpdateBy("mapper");
            emp.setEid(snowflake.nextId());
        });
        int insertBatch = empMapper.insertBatch(emps);
        Assertions.assertEquals(emps.size(), insertBatch);
    }

    @Test
    void updateSelective(){
        Emp emp = empMapper.selectPrimary(Emp.builder().empNum("45206650880").build());
        log.info(JSON.toJSONString(emp));
        if(!ObjectUtils.isEmpty(emp)){
            emp.setSalary(emp.getSalary().multiply(BigDecimal.TEN));
            emp.setUpdateBy("mapperUpdate");
            int updateSelective = empMapper.updateSelective(emp);
            Assertions.assertEquals(1, updateSelective);
        }
    }

    @Test
    void deleteSelective(){
        Emp emp = empMapper.selectPrimary(Emp.builder().empNum("90715084800").build());
        if(ObjectUtils.isEmpty(emp)) return;
        log.info(JSON.toJSONString(emp));
        emp.setEid(null);
        int deleteSelective = empMapper.deleteSelective(emp);
        Assertions.assertEquals(1, deleteSelective);
    }

    @Test
    void deleteBatch(){
        List<Emp> emps = empMapper.selectSelective(Emp.builder().build());
        int deleteBatch = empMapper.deleteBatch(emps.stream().map(Emp::getEid).collect(Collectors.toList()));
        Assertions.assertEquals(emps.size(), deleteBatch);
    }

    @Test
    void selectPrimarykey(){
        Emp emp = empMapper.selectPrimarykey(1543113991679774724L);
        log.info(JSON.toJSONString(emp));
    }

    @Test
    void selectForeignkey(){
        List<Emp> emps = empMapper.selectForeignkey(24L);
        log.info(JSON.toJSONString(emps));
    }

    @Test
    void selectSelectiveLinkEmps(){
        List<Emp> emps = empMapper.selectSelectiveJoinDep(Emp.builder().build());
        log.info(JSON.toJSONString(emps));
    }
}
