package com.zlingchun.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.constant.ResultConstant;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.service.dto.IDepDtoService;
import io.swagger.annotations.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/18
 * @description descrip
 */
@RestController
@Validated
@RequestMapping("dep")
@Api(value = "部门接口文档", tags = {"部门相关接口"})
public class DepController {

    @Resource
    IDepDtoService depDtoService;

    @ApiOperation(value = "新增部门",
            notes = "新增部门：根据部门名称查询\n" +
                    "1. 如果已经存在，那么不新增\n" +
                    "2. 如果不存在，那么就新增",
            httpMethod = "POST", response = String.class, tags = {"新增"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "depDto", value = "新增的部门详情", required = true, paramType = "body", dataTypeClass = DepDto.class)
    })
    @ApiResponses({
            @ApiResponse(code=400,message="请求参数没填好"),
            @ApiResponse(code=404,message="请求路径没有或页面跳转路径不对")
    })
    @PostMapping
    String saveDep(@RequestBody @Valid DepDto depDto){
        boolean save = depDtoService.save(depDto);
        return save ? ResultConstant.SAVE.getDesc() : ResultConstant.SAVEFAILED.getDesc();
    }


    @ApiOperation(value = "删除部门",
            notes = "删除部门：根据输入部门条件匹配后删除\n",
            httpMethod = "DELETE", response = String.class, tags = {"删除"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "depDto", value = "需要删除的部门条件", required = true, paramType = "body", dataTypeClass = DepDto.class)
    })
    @DeleteMapping
    String removeDep(@RequestBody @Valid DepDto depDto){
        boolean remove = depDtoService.remove(depDto);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @ApiOperation(value = "删除部门",
            notes = "删除部门：根据部门主键Id删除\n",
            httpMethod = "DELETE", response = String.class, tags = {"删除"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "需要删除的部门Id", required = true, paramType = "path", dataTypeClass = Long.class)
    })
    @DeleteMapping("{id}")
    String removeDep(@PathVariable("id") Long id){
        boolean remove = depDtoService.remove(id);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @ApiOperation(value = "更新部门",
            notes = "更新部门：根据部门主键Id更新\n",
            httpMethod = "PUT", response = String.class, tags = {"更新"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "需要更新的部门Id", required = true, paramType = "path", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "depDto", value = "需要更新的信息", required = true, paramType = "body", dataTypeClass = DepDto.class)
    })
    @PutMapping("{id}")
    String updateDep(@PathVariable("id") Long id, @RequestBody @Valid DepDto depDto){
        boolean update = depDtoService.update(id, depDto);
        return update ? ResultConstant.UPDATE.getDesc() : ResultConstant.UPDATEFAILED.getDesc();
    }

    @ApiOperation(value = "查询部门集合",
            notes = "查询部门集合：根据输入部门条件查询出部门集合\n",
            httpMethod = "GET", responseContainer = "List", tags = {"查询"})
    @GetMapping("list")
    List<DepDto> getDepList(DepDto depDto){
        return depDtoService.list(depDto);
    }

    @ApiOperation(value = "分页查询部门",
            notes = "分页查询部门：根据输入部门条件查询出部门集合\n" +
                    "分页查询必须输入pageNum和pageSize",
            httpMethod = "GET", response = Page.class, tags = {"查询"})
    @GetMapping("page")
    Page<DepDto> getDepPage(DepDto depDto){
        return depDtoService.page(depDto);
    }

    @ApiOperation(value = "查询单个部门信息",
            notes = "查询单个部门信息：根据部门ID，或者部门编号，或者部门名称查询单个部门信息\n",
            httpMethod = "GET", response = DepDto.class, tags = {"查询"})
    @ApiImplicitParams(value = {
            @ApiImplicitParam(name = "id", value = "查询的部门Id", required = true, paramType = "path", dataTypeClass = Long.class),
            @ApiImplicitParam(name = "depNo", value = "查询的部门编号", paramType = "query", dataTypeClass = String.class),
            @ApiImplicitParam(name = "depName", value = "查询的部门名称", paramType = "query", dataTypeClass = String.class)
    })
    @GetMapping("{id}")
    DepDto getDepOne(@PathVariable("id") Long id, @RequestParam(required = false) String depNo, @RequestParam(required = false) String depName){
        return depDtoService.findDepOne(DepDto.builder().id(id).depNo(depNo).depName(depName).build());
    }
}