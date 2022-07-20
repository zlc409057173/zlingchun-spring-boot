package com.zlingchun.mybatisplus.util.mapstruct;

import com.zlingchun.mybatisplus.util.mapstruct.annotations.MapStructTranslator;
import com.zlingchun.mybatisplus.util.mapstruct.annotations.SexChineseToNum;
import com.zlingchun.mybatisplus.util.mapstruct.annotations.SexNumToChinese;

/**
 * @author achun
 * @create 2022/7/17
 * @description descrip
 */
@MapStructTranslator
public class MapStructAnnotationHelper {

    /**
     * 将 0 和 1 分别映射为 男 和 女
     * @param index 0：男  1：女
     * @return
     */
    @SexNumToChinese
    public String sexChar2Str(Integer index){
        if(index == 0){
            return "男";
        }
        return "女";
    }

    /**
     * 将 男 和 女 分别映射为 0 和 1
     * @param sex 0：男  1：女
     * @return
     */
    @SexChineseToNum
    public Integer sexStr2Char(String sex){
        if("男".equals(sex)){
            return 0;
        }
        return 1;
    }
}
