package com.zlingchun.mybatis.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.pojo.Emp;
import com.zlingchun.mybatis.listener.BaseListener;
import com.zlingchun.mybatis.service.EmpService;
import com.zlingchun.mybatis.utils.FileUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

    @PostMapping("file")
    public String upload(MultipartFile file){
        if(file.isEmpty()) return "failed";
        try {
            EasyExcel.read(file.getInputStream(), Emp.class, new BaseListener(empServiceImpl)).sheet().doRead();
        }catch (IOException e){
            return "failed";
        }
        return "success";
    }

    @GetMapping("file")
    public void download(HttpServletResponse response, Emp emp) throws IOException {
        List<Emp> emps = empServiceImpl.findSelective(emp);
        try {
            response.setCharacterEncoding("utf-8");
            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            // 这里URLEncoder.encode可以防止中文乱码 当然和easyexcel没有关系
            String fileName = URLEncoder.encode("员工信息" , "UTF-8").replaceAll("\\+", "%20");
            response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
            // 这里需要设置不关闭流
            Set<String> includeColumnFiledNames = new HashSet<>();
            includeColumnFiledNames.add("empName");
            includeColumnFiledNames.add("empNum");
            includeColumnFiledNames.add("sex");
            includeColumnFiledNames.add("telNumber");
            includeColumnFiledNames.add("salary");
            includeColumnFiledNames.add("birthday");
            includeColumnFiledNames.add("empAddress");
            includeColumnFiledNames.add("email");
            includeColumnFiledNames.add("dep");
            FileUtils.excelDownloadIncloudFields(response, emps, "员工信息", Emp.class, includeColumnFiledNames);
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
