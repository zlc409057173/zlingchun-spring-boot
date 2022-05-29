package com.zlingchun.jpa.utils;

import org.junit.jupiter.api.Test;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
public class DruidEncryptorUtilsTest {

    @Test
    void testEncode(){
        DruidEncryptorUtils.encode("12345678");
    }

    @Test
    void testDecode(){
        DruidEncryptorUtils.decode("");
    }
}
