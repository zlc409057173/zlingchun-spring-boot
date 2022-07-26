package com.zlingchun.mybatisplus.service.dto.impl;

import cn.hutool.core.lang.UUID;
import cn.hutool.crypto.digest.DigestUtil;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.zlingchun.mybatisplus.constant.RedisConstant;
import com.zlingchun.mybatisplus.converter.mapstruct.UserConvert;
import com.zlingchun.mybatisplus.doman.dto.UserDto;
import com.zlingchun.mybatisplus.doman.pojo.User;
import com.zlingchun.mybatisplus.handler.response.enums.ReturnCode;
import com.zlingchun.mybatisplus.service.dto.IUserDtoService;
import com.zlingchun.mybatisplus.service.pojo.IUserService;
import com.zlingchun.mybatisplus.util.commons.RedisUtils;
import com.zlingchun.mybatisplus.util.security.JJWTUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@Service
public class UserDtoServiceImpl implements IUserDtoService {

    @Resource
    IUserService userService;

    @Resource
    UserConvert userConvert;

    @Resource
    ObjectMapper objectMapper;

    @Resource
    RedisUtils redisUtils;

    @Override
    public boolean saveBatch(List<UserDto> data) {
        return false;
    }

    @Override
    public UserDto getUser(String userCode) {
        LambdaQueryWrapper<User> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(User::getUserCode, userCode);
        User user = userService.getOne(lambdaQueryWrapper);
        return userConvert.user2UserDto(user);
    }

    @Override
    public Map<String, Object> login(String usercode, String loginPass){
        UserDto user = this.getUser(usercode);
        if(Objects.isNull(user)){
            throw new IllegalArgumentException(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }
        String userPassword = user.getPassword();
        String password = DigestUtil.md5Hex(loginPass);
        if(!StringUtils.equalsIgnoreCase(userPassword, password)){
            throw new IllegalArgumentException(ReturnCode.USERNAME_OR_PASSWORD_ERROR.getMessage());
        }
        Map<String, Object> payload = new HashMap<>(1);
        // 生成随机UUID作为key, 存入Redis
        String uuid = UUID.randomUUID().toString();
        String userStr = JSON.toJSONString(user);
        redisUtils.set(RedisConstant.USER + uuid, userStr, RedisConstant.USER_LOGIN_CACHE_TIME);
        payload.put("uuid", uuid);
        payload.put("userCode", usercode);
        payload.put("userId", user.getId());
        // 将生成的key作为payload生成JWT token
        String token = JJWTUtils.getJwtToken(payload);
        Map<String, Object> map = new HashMap<>(6);
        map.put("uuid", uuid);
        map.put("token", token);
        map.put("msg", "登录成功");
        return map;
    }

    @Override
    public UserDto verify(String uuid, String userCode, Long userId){
        String userStr = redisUtils.get(RedisConstant.USER + uuid).toString();
        UserDto userDto = JSON.parseObject(userStr, UserDto.class);
        if(Objects.nonNull(userDto)){
            return userDto;
        }
        UserDto user = this.getUser(userCode);
        if(Objects.nonNull(user)){
            redisUtils.set(RedisConstant.USER + uuid, userStr, RedisConstant.USER_LOGIN_CACHE_TIME);
        }
        return user;
    }
}
