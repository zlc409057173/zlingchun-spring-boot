package com.zlingchun.mybatis.service;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.util.ListUtils;
import com.zlingchun.mybatis.converter.mapper.EmpDtoMapper;
import com.zlingchun.mybatis.entity.dto.EmpDto;
import com.zlingchun.mybatis.entity.pojo.Dep;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.utils.test.RandomInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class EmpServiceTest {

    @Resource
    private EmpService empService;

    @Resource
    private Snowflake snowflake;

    @Autowired
    EmpDtoMapper empDtoMapper;

    private static final BigDecimal[] salary = {BigDecimal.valueOf(8000.01), BigDecimal.valueOf(10000.5), BigDecimal.valueOf(3000)};
    private static final String[] sexs = {"0", "1"};
    private static final String[] depNames = {"总经办", "研发", "测试", "产品"};
    private static List<EmpDto> empDtos = ListUtils.newArrayList();

    @BeforeEach
    void baseData() {
        Random random = new Random();
        List<Emp> emps = ListUtils.newArrayList();
        for (int i = 0; i < 100000; i++) {
            emps.add(Emp.builder()
                    .empName(RandomInfo.getRandomName(null))
                    .empNum(snowflake.nextIdStr().substring(8))
                    .telNumber(RandomInfo.createMobile(random.nextInt(3)))
                    .email(RandomInfo.getRandomQQEmail())
                    .birthday(RandomInfo.getRandomBirthday())
                    .salary(salary[random.nextInt(salary.length)].multiply(BigDecimal.valueOf(random.nextFloat())).setScale(2, RoundingMode.HALF_UP))
                    .sex(sexs[random.nextInt(sexs.length)])
                    .empAddress(RandomInfo.getRandomAddress())
                    .dep(Dep.builder().depName(depNames[RandomInfo.randomInt(depNames.length)]).build())
                    .build());
        }
        empDtos = empDtoMapper.emp2EmpDto(emps);
    }

    @Test
    void save(){
        int save = empService.save(empDtos.get(0));
        Assertions.assertNotEquals(0, save);
    }

    @Test
    void exit(){
        EmpDto exit = empService.exit(empDtos.get(0));
        Assertions.assertNotEquals(0, exit);
    }

    @Test
    void saveBatch(){
        int saveBatch = empService.saveBatch(empDtos);
        Assertions.assertNotEquals(0, saveBatch);
    }
}
