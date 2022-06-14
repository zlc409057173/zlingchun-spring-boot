package com.zlingchun.mybatis.controller;

import com.cxytiandi.encrypt.springboot.annotation.Decrypt;
import com.cxytiandi.encrypt.springboot.annotation.Encrypt;
import org.springframework.web.bind.annotation.*;

/**
 * @author achun
 * @create 2022/6/14
 * @description descrip
 */
@RestController
public class EncryptController {

    @Encrypt
    @GetMapping(value = "encrypt")
    public String encrypt(){
        return "张三";
    }

    @Decrypt(value = "get:/decrypt", decyptParam = "name")
    @GetMapping(value = "decrypt")
    public String encrypt(@RequestParam(value = "name") String name){
        return "hello, " + name;
    }

    @GetMapping(value = "test")
    public String test(){
        return "hello, test ";
    }

}
