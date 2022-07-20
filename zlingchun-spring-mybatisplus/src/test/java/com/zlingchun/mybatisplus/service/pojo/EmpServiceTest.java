package com.zlingchun.mybatisplus.service.pojo;

import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class EmpServiceTest {

    @Resource
    IEmpService empService;

    @Test
    void list(){
        List<Emp> list = empService.list();
        log.info(JSON.toJSONString(list));
    }
}
