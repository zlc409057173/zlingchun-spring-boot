package com.zlingchun.mybatisplus.config;

import com.zlingchun.mybatisplus.doman.dto.UserDto;

public class UserContext {
    
    private static ThreadLocal<UserDto> userThread = new ThreadLocal<>();


    public static void set(UserDto userDto) {
        userThread.set(userDto);
    }

    public static UserDto get() {
        return userThread.get();
    }

    /**
     * 获取当前登录用户的ID
     * 未登录返回null
     *
     * @return
     */
    public static Long getUserId() {
        UserDto user = get();
        if (user != null && user.getId() != null) {
            return user.getId();
        }
        return null;
    }

    //防止内存泄漏
    public static void remove() {
        userThread.remove();
    }
}