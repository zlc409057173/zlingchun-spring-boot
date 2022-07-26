package com.zlingchun.mybatisplus.service.pojo.imp;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlingchun.mybatisplus.doman.pojo.User;
import com.zlingchun.mybatisplus.mapper.UserMapper;
import com.zlingchun.mybatisplus.service.pojo.IUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author achun
 * @create 2022/7/26
 * @description descrip
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Resource
    UserMapper userMapper;
}
