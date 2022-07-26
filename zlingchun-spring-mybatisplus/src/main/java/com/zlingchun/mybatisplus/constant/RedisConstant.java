package com.zlingchun.mybatisplus.constant;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
public class RedisConstant {

    /**
     * redis 用户登录失效时间
     */
    public static final Long USER_LOGIN_CACHE_TIME = 24*60*60*1000L;

    public static final String USER = "LOGIN:USER:";
}
