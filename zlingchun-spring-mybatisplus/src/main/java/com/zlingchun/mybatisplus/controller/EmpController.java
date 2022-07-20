package com.zlingchun.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.constant.ResultConstant;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;
import com.zlingchun.mybatisplus.service.dto.IEmpDtoService;
import com.zlingchun.mybatisplus.validator.ValidGroup;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
@RestController
@Validated
@RequestMapping("emp")
public class EmpController {

    @Resource
    IEmpDtoService empDtoService;

    @PostMapping
    String saveEmp(@RequestBody @Valid EmpDto empDto){
        boolean save = empDtoService.save(empDto);
        return save ? ResultConstant.SAVE.getDesc() : ResultConstant.SAVEFAILED.getDesc();
    }

    @DeleteMapping
    String removeEmp(@RequestBody @Valid EmpDto empDto){
        boolean remove = empDtoService.remove(empDto);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @DeleteMapping("{id}")
    String removeEmp(@PathVariable("id") @NotNull(message = "删除的员工主键不能为空", groups = ValidGroup.Crud.Delete.class) Long id){
        boolean remove = empDtoService.remove(id);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @PutMapping("{id}")
    String updateEmp(@PathVariable("id") @NotNull Long id, @RequestBody @Valid EmpDto empDto){
        boolean update = empDtoService.update(id, empDto);
        return update ? ResultConstant.UPDATE.getDesc() : ResultConstant.UPDATEFAILED.getDesc();
    }

    @GetMapping("list")
    List<EmpDto> getEmpList(@RequestBody @Valid EmpDto empDto){
        return empDtoService.findEmpList(empDto);
    }

    @GetMapping("page")
    Page<EmpDto> getEmpPage(@RequestBody @Valid EmpDto empDto){
        return empDtoService.findEmpPage(empDto);
    }

    @GetMapping("{id}")
    EmpDto getEmpOne(@PathVariable("id") @NotNull Long id, @RequestParam(required = false) String empNo, @RequestParam(required = false) String empPhone){
        return empDtoService.findEmpOne(id, empNo, empPhone);
    }

    @GetMapping("only/page")
    Page<EmpDto> getOnlyEmpPage(@RequestBody @Valid EmpDto empDto){
        return empDtoService.findOnlyEmpPage(empDto);
    }

    @GetMapping("only/list")
    List<EmpDto> getOnlyEmpList(@RequestBody @Valid EmpDto empDto){
        return empDtoService.findOnlyEmpList(empDto);
    }

    @GetMapping("only/{id}")
    EmpDto getOnlyEmpOne(@PathVariable("id") @NotNull Long id, @RequestParam(required = false) String empNo, @RequestParam(required = false) String empPhone){
        return empDtoService.findOnlyEmpOne(id, empNo, empPhone);
    }
}
