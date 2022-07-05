package com.zlingchun.mybatis.utils;

import com.zlingchun.mybatis.utils.commons.PinyinUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

/**
 * @author achun
 * @create 2022/7/5
 * @description descrip
 */
@Slf4j
public class PinyinUtilTest {

    @Test
    void getFullSpell(){
        log.info(PinyinUtil.getFullSpell("我爱你"));
    }
}
