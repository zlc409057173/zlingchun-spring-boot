package com.zlingchun.jpa.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author achun
 * @create 2022/5/28
 * @description descrip
 */

@RestController
@RequestMapping("/test")
public class TestController {


    @GetMapping
    public String hello(@RequestParam(required = false, value = "name") String name){
        return "hello : " + name;
    }

}
