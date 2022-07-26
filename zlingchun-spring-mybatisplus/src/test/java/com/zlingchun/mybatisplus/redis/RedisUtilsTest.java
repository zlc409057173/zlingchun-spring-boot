package com.zlingchun.mybatisplus.redis;

import cn.hutool.core.lang.UUID;
import com.zlingchun.mybatisplus.util.commons.RedisUtils;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@SpringBootTest
public class RedisUtilsTest {

    @Resource
    RedisUtils redisUtils;

    @Test
    void set(){
        String uuid = UUID.randomUUID().toString();

        redisUtils.set("uuid", uuid);
    }
}
