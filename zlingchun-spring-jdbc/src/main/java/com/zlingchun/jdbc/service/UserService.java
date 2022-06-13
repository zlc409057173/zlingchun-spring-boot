package com.zlingchun.jdbc.service;

import com.zlingchun.jdbc.entity.User;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
public interface UserService {
    User getUser(Long id);
    int addUser(User user);
    int modifyUser(User user);
    int removeUser(Long id);
}
