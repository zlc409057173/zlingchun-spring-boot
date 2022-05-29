package com.zlingchun.jdbc.utils;

import org.junit.jupiter.api.Test;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
public class DruidEncryptorUtilsTest {

    @Test
    void testEncode(){
        DruidEncryptorUtils.encode("");
    }

    @Test
    void testDecode(){
        DruidEncryptorUtils.decode("");
    }
}
