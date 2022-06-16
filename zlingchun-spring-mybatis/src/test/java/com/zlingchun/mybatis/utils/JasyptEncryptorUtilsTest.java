package com.zlingchun.mybatis.utils;

import org.junit.jupiter.api.Test;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
public class JasyptEncryptorUtilsTest {

    @Test
    void testEncode(){
        JasyptEncryptorUtils.encode("jdbc:mysql://119.23.189.235:3306/mybatis?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai");
    }

    @Test
    void testDecode(){
        JasyptEncryptorUtils.decode("H7kWSc7RXUbutQyhdiAAv9ljOU8cPOZGMWn8YJjNGGoTxCS4PGehGOybafXfACHuzVeyczwhB4/mRVKEO8iVvVy4taEKgfLvCDexcA1voCUbZk2vzDKBTNUztRnvkBYQ3058YTUVkxiqyWtHS3S5uKXcqYvAoQjOMfHv/6QIfis=");
    }
}
