package com.zlingchun.mybatis.mapper.mapStruct;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.zlingchun.mybatis.converter.mapper.EmpMapper;
import com.zlingchun.mybatis.entity.dto.EmpDto;
import com.zlingchun.mybatis.entity.pojo.Dep;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.utils.test.RandomInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.util.StopWatch;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

/**
 * @author achun
 * @create 2022/7/3
 * @description descrip
 */
@Slf4j
public class EmpDtoMapperTest {

    private static final BigDecimal[] salary = {BigDecimal.valueOf(8000.01), BigDecimal.valueOf(10000.5), BigDecimal.valueOf(3000)};
    private static final String[] sexs = {"0", "1"};
    private static final String[] depNames = {"总经办", "研发", "测试", "产品"};
    private static List<Emp> emps = ListUtils.newArrayList();

    @BeforeEach
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
                    .dep(Dep.builder().depName(depNames[RandomInfo.randomInt(depNames.length)]).did(RandomInfo.randomInt(100)).build())
                    .build());
        }
    }

    @Test
    void toEmpDto(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始拷贝！");
        Emp emp = emps.get(0);
        log.info("源数据：{}",JSON.toJSONString(emp));
        EmpDto empDto = EmpMapper.INSTANCE.emp2EmpDto(emp);
        stopWatch.stop();
        log.info("转换数据：{}",JSON.toJSONString(empDto));
        log.info("花费时间 {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void toEmp(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始拷贝！");
        EmpDto empDto = EmpMapper.INSTANCE.baseData();
        log.info("源数据：{}",JSON.toJSONString(empDto));
        Emp emp = EmpMapper.INSTANCE.empDto2Emp(empDto);
        stopWatch.stop();
        log.info("转换数据：{}",JSON.toJSONString(emp));
        log.info("花费时间 {} ms", stopWatch.getTotalTimeMillis());
    }

    @Test
    void updateEmpDto(){
        StopWatch stopWatch = new StopWatch();
        stopWatch.start("开始拷贝！");
        EmpDto empDto = EmpMapper.INSTANCE.baseData();
        log.info("target数据：{}",JSON.toJSONString(empDto));
        Emp emp = emps.get(0);
        log.info("source数据：{}",JSON.toJSONString(emp));
        EmpMapper.INSTANCE.updateEmpDto(emp, empDto);
        stopWatch.stop();
        log.info("转换数据：{}",JSON.toJSONString(empDto));
        log.info("花费时间 {} ms", stopWatch.getTotalTimeMillis());
    }

}
