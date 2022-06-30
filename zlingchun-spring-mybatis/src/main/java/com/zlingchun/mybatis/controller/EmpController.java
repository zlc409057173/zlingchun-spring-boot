package com.zlingchun.mybatis.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson2.JSON;
import com.github.pagehelper.PageInfo;
import com.zlingchun.mybatis.entity.po.Emp;
import com.zlingchun.mybatis.listener.EmpListener;
import com.zlingchun.mybatis.service.EmpService;
import com.zlingchun.mybatis.utils.FileUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

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
    public PageInfo<Emp> getEmps(Emp emp){
        List<Emp> emps = empServiceImpl.findEmps(emp);
        PageInfo<Emp> info = new PageInfo<>(emps);
        return info;
    }

    @PostMapping("file")
    public String upload(MultipartFile file){
        try {
            EasyExcel.read(file.getInputStream(), Emp.class, new EmpListener(empServiceImpl)).sheet().doRead();
        }catch (IOException e){
            return "failed";
        }
        return "success";
    }

    @GetMapping("file")
    public void download(HttpServletResponse response, @RequestParam("empName") String empName, @RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) throws IOException {
        List<Emp> emps = empServiceImpl.findEmps(Emp.builder().empName(empName).build(), pageNum, pageSize);
        try {
            FileUtils.excelDownload(response, emps, "员工信息", Emp.class);
        } catch (Exception e) {
            // 重置response
            response.reset();
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/json");
            Map<String, String> map = MapUtils.newHashMap();
            map.put("status", "failure");
            map.put("message", "下载文件失败" + e.getMessage());
            response.getWriter().println(JSON.toJSONString(map));
        }
    }

}
