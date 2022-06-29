package com.zlingchun.mybatis.controller;

import com.zlingchun.mybatis.entity.po.Emp;
import com.zlingchun.mybatis.service.EmpService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
@RestController
@RequestMapping(value = "emp")
public class EmpController {

    @Resource
    EmpService empServiceImpl;

    @GetMapping
    public List<Emp> getEmps(@RequestParam(value = "empName") String empName){
        return empServiceImpl.findEmps(empName);
    }

}
