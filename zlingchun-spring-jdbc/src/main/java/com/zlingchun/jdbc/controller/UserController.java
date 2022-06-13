package com.zlingchun.jdbc.controller;

import com.zlingchun.jdbc.entity.User;
import com.zlingchun.jdbc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    UserService userService;

    @GetMapping(value = "/{id}")
    public User find(@PathVariable("id") Long id){
        return userService.getUser(id);
    }

    @DeleteMapping(value = "/{id}")
    public int move(@PathVariable(value = "id") Long id){
        return userService.removeUser(id);
    }

    @PostMapping
    public int add(@RequestBody User user){
        return userService.addUser(user);
    }

    @PutMapping
    public int modify(@RequestBody User user){
        return userService.modifyUser(user);
    }
}
