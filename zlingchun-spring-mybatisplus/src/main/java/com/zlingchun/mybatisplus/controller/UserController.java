package com.zlingchun.mybatisplus.controller;

import com.zlingchun.mybatisplus.service.dto.IUserDtoService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.util.Map;

/**
 * @author achun
 * @create 2022/7/25
 * @description descrip
 */
@Validated
@RestController
@RequestMapping(value = "user")
public class UserController {

    @Resource
    IUserDtoService userDtoService;

    @PostMapping("login")
    public Map<String, Object> login(@RequestParam @NotNull(message = "请输入登录用户") String usercode, @RequestParam @NotNull(message = "请输入密码") String password){
        return userDtoService.login(usercode, password);
    }

}
