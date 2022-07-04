package com.zlingchun.mybatis.utils;

import com.zlingchun.mybatis.constant.MapStructConstant;
import com.zlingchun.mybatis.utils.commons.Desensitization;
import org.mapstruct.Named;

/**
 * @author achun
 * @create 2022/7/4
 * @description descrip
 */
@Named(MapStructConstant.MAP_STRUCT_HELPER)
public class MapStructHelper {

    /**
     * 将 0 和 1 分别映射为 男 和 女
     * @param index 0：男  1：女
     * @return
     */
    @Named(MapStructConstant.SEX_CHAR_2_STR)
    public static String sexChar2Str(String index){
        if("0".equals(index)){
            return "男";
        }
        return "女";
    }

    /**
     * 将 男 和 女 分别映射为 0 和 1
     * @param sex 0：男  1：女
     * @return
     */
    @Named(MapStructConstant.SEX_STR_2_CHAR)
    public static String sexStr2Char(String sex){
        if("男".equals(sex)){
            return "0";
        }
        return "1";
    }

    /**
     * 手机号敏感化处理
     * @param telNumber
     * @return
     */
    @Named(MapStructConstant.SENSITIVE_TELNUMBER)
    public static String sensitiveTelNumber(String telNumber){
        return Desensitization.mobilePhoneDesensitization(telNumber);
    }

}
