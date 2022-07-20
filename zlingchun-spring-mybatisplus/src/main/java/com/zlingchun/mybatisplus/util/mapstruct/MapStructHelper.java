package com.zlingchun.mybatisplus.util.mapstruct;

import com.zlingchun.mybatisplus.util.commons.Desensitization;
import com.zlingchun.mybatisplus.util.mapstruct.constant.MapStructConstant;
import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author achun
 * @create 2022/7/4
 * @description descrip
 */
@Component
@Named(MapStructConstant.MAP_STRUCT_HELPER)
public class MapStructHelper {

    /**
     * 手机号敏感化处理
     * @param telNumber
     * @return
     */
    @Named(MapStructConstant.SENSITIVE_PHONE)
    public static String sensitivePhone(String telNumber){
        return Desensitization.mobilePhoneDesensitization(telNumber);
    }

    /**
     * 家庭住址脱敏化处理
     * @param address
     * @return
     */
    @Named(MapStructConstant.SENSITIVE_ADDRESS)
    public static String sensitiveAddress(String address){
        return Desensitization.addressDesensitization(address);
    }

    /**
     * 将 0 和 1 分别映射为 男 和 女
     * @param index 0：男  1：女
     * @return
     */
    @Named(MapStructConstant.SEX_NUMTOCHINESE)
    public static String sexChar2Str(Integer index){
        if (Objects.isNull(index)) return null;
        switch (index) {
            case 0 :
                return "男";
            case 1 :
                return "女";
            default:
                return null;
        }
    }

    /**
     * 将 男 和 女 分别映射为 0 和 1
     * @param sex 0：男  1：女
     * @return
     */
    @Named(MapStructConstant.SEX_CHINESETONUM)
    public static Integer sexStr2Char(String sex){
        if(StringUtils.isEmpty(sex)) return null;
        switch (sex) {
            case "男" :
                return 0;
            case "女" :
                return 1;
            default:
                return null;
        }
    }
}
