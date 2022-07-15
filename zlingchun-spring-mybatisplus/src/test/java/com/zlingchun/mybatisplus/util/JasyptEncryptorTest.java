package com.zlingchun.mybatisplus.util;

import com.zlingchun.mybatisplus.util.test.JasyptEncryptorUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

/**
 * @author achun
 * @create 2022/7/6
 * @description descrip
 */
@Slf4j
public class JasyptEncryptorTest {

    @Test
    void encode(){
        JasyptEncryptorUtils.encode("jdbc:mysql://192.168.178.25:3306/mybatis_plus?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
    }

    @Test
    @Disabled
    void decode(){
        JasyptEncryptorUtils.encode("");
    }
}
