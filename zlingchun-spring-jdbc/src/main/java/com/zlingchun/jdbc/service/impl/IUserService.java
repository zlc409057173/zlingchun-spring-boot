package com.zlingchun.jdbc.service.impl;

import cn.hutool.core.lang.Snowflake;
import com.zlingchun.jdbc.dao.UserDao;
import com.zlingchun.jdbc.entity.User;
import com.zlingchun.jdbc.properties.CustomEncryptProperties;
import com.zlingchun.jdbc.service.UserService;
import com.zlingchun.jdbc.utils.EncryptorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@Service
public class IUserService implements UserService {
    @Autowired
    UserDao dao;
    @Autowired
    Snowflake snowflake;
    @Autowired
    CustomEncryptProperties properties;

    @Override
    public User getUser(Long id) {
        User user = dao.select(User.builder().id(id).build());
        if(!ObjectUtils.isEmpty(user)){
            user.setPassword(EncryptorUtils.decode(properties.getSecretKey(), user.getPassword()));
        }
        return user;
    }

    @Override
    public int addUser(User user) {
        user.setId(snowflake.nextId());
        user.setPassword(EncryptorUtils.encode(properties.getSecretKey(), user.getPassword()));
        return dao.insert(user);
    }

    @Override
    public int modifyUser(User user) {
        user.setPassword(EncryptorUtils.encode(properties.getSecretKey(), user.getPassword()));
        return dao.update(user);
    }

    @Override
    public int removeUser(Long id) {
        return dao.delete(User.builder().id(id).build());
    }
}
