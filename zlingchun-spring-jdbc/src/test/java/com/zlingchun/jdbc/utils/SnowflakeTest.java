package com.zlingchun.jdbc.utils;

import cn.hutool.core.lang.Assert;
import cn.hutool.core.lang.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class SnowflakeTest {
    @Autowired
    Snowflake snowflake;

    @Test
    void getLongId(){
        log.info("生成了LongId, {}", snowflake.nextId());
        Assert.notNull(snowflake.nextId());
    }

    @Test
    void getStrId(){
        log.info("生成了StrId, {}", snowflake.nextIdStr());
    }

}
