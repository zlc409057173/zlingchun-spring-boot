package com.zlingchun.mybatis.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.MapUtils;
import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.dto.EmpDto;
import com.zlingchun.mybatis.listener.BaseListener;
import com.zlingchun.mybatis.service.EmpService;
import com.zlingchun.mybatis.utils.commons.FileUtils;
import com.zlingchun.mybatis.validator.ValidGroup;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
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
@Slf4j
@RestController
@RequestMapping(value = "emp")
@Validated
public class EmpController {
    @Resource
    EmpService empServiceImpl;

    @PostMapping("file")
    public String upload(MultipartFile file){
        if(file.isEmpty()) return "failed";
        try {
            EasyExcel.read(file.getInputStream(), EmpDto.class, new BaseListener(empServiceImpl)).sheet().doRead();
        }catch (IOException e){
            return "failed";
        }
        return "success";
    }

    @GetMapping("file")
    public void download(HttpServletResponse response, EmpDto empDto) throws IOException {
        List<EmpDto> empDtos = empServiceImpl.findSelective(empDto);
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
            FileUtils.excelDownloadIncloudFields(response, empDtos, "员工信息", EmpDto.class, includeColumnFiledNames);
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


    @PostMapping
    public String save(@RequestBody @Validated(value = {ValidGroup.Crud.Create.class}) EmpDto empDto){
        int save = empServiceImpl.save(empDto);
        Assert.isTrue( save == 1, "保存失败！");
        return "success";
    }

    @DeleteMapping(value = "{eid}")
    public String remove(@PathVariable("eid") @NotBlank Long eid){
        int remove = empServiceImpl.remove(eid);
        Assert.isTrue( remove == 1, "删除失败！");
        return "success";
    }

    @PutMapping
    public String update(@Validated(value = {ValidGroup.Crud.Update.class}) EmpDto empDto){
        int modify = empServiceImpl.modify(empDto);
        Assert.isTrue( modify == 1, "修改失败！");
        return "success";
    }

    @GetMapping
    public List<EmpDto> findSelective(@Validated(value = {ValidGroup.Crud.Query.class}) EmpDto empDto){
        List<EmpDto> empDtos = empServiceImpl.findSelective(empDto);
        log.info("查询数据：{}", JSON.toJSONString(empDtos));
        return empDtos;
    }

    @GetMapping("{eid}")
    public EmpDto findPrimary(@PathVariable("eid") @NotNull Long eid){
        EmpDto empDto = empServiceImpl.findPrimarykey(eid);
        log.info("查询数据：{}", JSON.toJSONString(empDto));
        return empDto;
    }
}
