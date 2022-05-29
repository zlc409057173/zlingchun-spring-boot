package com.zlingchun.jdbc.utils;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
@Slf4j
public class EncryptorUtilsTest {

    @Test
    void testEncode(){
        EncryptorUtils.encode("fa21b79aa3652815c54c683ca627c784","root");
    }

    @Test
    void testDecode(){
        EncryptorUtils.decode("fa21b79aa3652815c54c683ca627c784","3d7c951066a6daae8ca85b899eb6edbf");
    }

}
