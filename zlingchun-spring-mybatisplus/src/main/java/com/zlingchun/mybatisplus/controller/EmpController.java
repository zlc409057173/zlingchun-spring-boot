package com.zlingchun.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.constant.ResultConstant;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;
import com.zlingchun.mybatisplus.service.dto.IEmpDtoService;
import com.zlingchun.mybatisplus.validator.ValidGroup;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
@RestController
@Validated
@RequestMapping("emp")
@Api(value = "员工接口文档", tags = {"员工相关接口"})
public class EmpController {

    @Resource
    IEmpDtoService empDtoService;

    @ApiOperation(value = "新增员工",
            notes = "新增员工：根据员工手机号查询\n" +
                    "1. 如果已经存在，那么不新增\n" +
                    "2. 如果不存在，那么就新增",
            httpMethod = "POST", response = String.class, tags = {"新增"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "empDto", value = "新增的员工详情", required = true, paramType = "body", dataTypeClass = EmpDto.class)
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping
    String saveEmp(@RequestBody @Validated(value = {ValidGroup.Crud.Create.class}) EmpDto empDto){
        boolean save = empDtoService.save(empDto);
        return save ? ResultConstant.SAVE.getDesc() : ResultConstant.SAVEFAILED.getDesc();
    }

    @ApiOperation(value = "删除员工",
            notes = "删除员工：根据输入员工条件匹配后删除\n",
            httpMethod = "DELETE", response = String.class, tags = {"删除"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "empDto", value = "需要删除的员工条件", required = true, paramType = "body", dataTypeClass = EmpDto.class)
    })
    @DeleteMapping
    String removeEmp(@RequestBody @Validated(value = {ValidGroup.Crud.Delete.class}) EmpDto empDto){
        boolean remove = empDtoService.remove(empDto);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @ApiOperation(value = "删除员工",
            notes = "删除员工：根据员工主键Id删除\n",
            httpMethod = "DELETE", response = String.class, tags = {"删除"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "需要删除的员工Id", required = true, paramType = "path", dataTypeClass = Long.class)
    })
    @DeleteMapping("{id}")
    String removeEmp(@PathVariable("id") Long id){
        boolean remove = empDtoService.remove(id);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @ApiOperation(value = "更新员工",
            notes = "更新员工：根据员工主键Id更新\n",
            httpMethod = "PUT", response = String.class, tags = {"更新"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "需要更新的员工Id", required = true, paramType = "path", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "empDto", value = "需要更新的信息", required = true, paramType = "body", dataTypeClass = EmpDto.class)
    })
    @PutMapping("{id}")
    String updateEmp(@PathVariable("id") Long id, @RequestBody @Validated(value = {ValidGroup.Crud.Update.class}) EmpDto empDto){
        boolean update = empDtoService.update(id, empDto);
        return update ? ResultConstant.UPDATE.getDesc() : ResultConstant.UPDATEFAILED.getDesc();
    }

    @ApiOperation(value = "关联查询员工集合",
            notes = "查询员工集合：根据输入员工条件查询出员工集合\n",
            httpMethod = "GET", responseContainer = "List", tags = {"查询"})
    @GetMapping("list")
    List<EmpDto> getEmpList(EmpDto empDto){
        return empDtoService.findEmpList(empDto);
    }

    @ApiOperation(value = "关联分页查询员工",
            notes = "分页查询员工：根据输入员工条件查询出员工集合\n" +
                    "分页查询必须输入pageNum和pageSize",
            httpMethod = "GET", response = Page.class, tags = {"查询"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "empDto", value = "查询的部门条件", required = true, dataTypeClass = EmpDto.class)
    })
    @GetMapping("page")
    Page<EmpDto> getEmpPage(@RequestBody @Validated(value = {ValidGroup.Crud.Query.class}) EmpDto empDto){
        return empDtoService.findEmpPage(empDto);
    }

    @ApiOperation(value = "关联查询单个员工信息",
            notes = "查询单个员工信息：根据员工ID，或者员工编号，或者员工电话查询单个员工信息\n",
            httpMethod = "GET", response = DepDto.class, tags = {"查询"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "查询的员工Id", required = true, paramType = "path", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "empNo", value = "查询的员工编号", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "empPhone", value = "查询的员工电话", paramType = "query", dataTypeClass = String.class)
    })
    @GetMapping("{id}")
    EmpDto getEmpOne(@PathVariable("id") Long id, @RequestParam(required = false) String empNo, @RequestParam(required = false) String empPhone){
        return empDtoService.findEmpOne(EmpDto.builder().id(id).empNo(empNo).phone(empPhone).build());
    }

    @ApiOperation(value = "分页查询员工",
            notes = "分页查询员工：根据输入员工条件查询出员工集合\n" +
                    "分页查询必须输入pageNum和pageSize",
            httpMethod = "GET", response = Page.class, tags = {"查询"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "empDto", value = "查询的部门条件", required = true, dataTypeClass = EmpDto.class)
    })
    @GetMapping("only/page")
    Page<EmpDto> getOnlyEmpPage(@RequestBody @Validated(value = {ValidGroup.Crud.Query.class}) EmpDto empDto){
        return empDtoService.findOnlyEmpPage(empDto);
    }

    @ApiOperation(value = "查询员工集合",
            notes = "查询员工集合：根据输入员工条件查询出员工集合\n",
            httpMethod = "GET", responseContainer = "List", tags = {"查询"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "empDto", value = "查询的员工条件", required = true, dataTypeClass = EmpDto.class)
    })
    @GetMapping("only/list")
    List<EmpDto> getOnlyEmpList(@RequestBody EmpDto empDto){
        return empDtoService.findOnlyEmpList(empDto);
    }

    @ApiOperation(value = "查询单个员工信息",
            notes = "查询单个员工信息：根据员工ID，或者员工编号，或者员工电话查询单个员工信息\n",
            httpMethod = "GET", response = DepDto.class, tags = {"查询"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "查询的员工Id", required = true, paramType = "path", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "empNo", value = "查询的员工编号", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "empPhone", value = "查询的员工电话", paramType = "query", dataTypeClass = String.class)
    })
    @GetMapping("only/{id}")
    EmpDto getOnlyEmpOne(@PathVariable("id") Long id, @RequestParam(required = false) String empNo, @RequestParam(required = false) String empPhone){
        return empDtoService.findOnlyEmpOne(EmpDto.builder().id(id).empNo(empNo).phone(empPhone).build());
    }
}
