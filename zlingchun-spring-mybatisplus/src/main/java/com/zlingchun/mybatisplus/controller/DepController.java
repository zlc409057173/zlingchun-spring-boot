package com.zlingchun.mybatisplus.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.constant.ResultConstant;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.service.dto.IDepDtoService;
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
@RequestMapping("dep")
public class DepController {

    @Resource
    IDepDtoService depDtoService;

    @PostMapping
    String saveDep(@RequestBody @Valid DepDto depDto){
        boolean save = depDtoService.save(depDto);
        return save ? ResultConstant.SAVE.getDesc() : ResultConstant.SAVEFAILED.getDesc();
    }

    @DeleteMapping
    String removeDep(@RequestBody @Valid DepDto depDto){
        boolean remove = depDtoService.remove(depDto);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @DeleteMapping("{id}")
    String removeDep(@PathVariable("id") @NotNull(message = "删除部门时, 主键不能为空", groups = ValidGroup.Crud.Delete.class) Long id){
        boolean remove = depDtoService.remove(id);
        return remove ? ResultConstant.REMOVE.getDesc() : ResultConstant.REMOVEFAILED.getDesc();
    }

    @PutMapping("{id}")
    String updateDep(@PathVariable("id") @NotNull Long id, @RequestBody @Valid DepDto depDto){
        boolean update = depDtoService.update(id, depDto);
        return update ? ResultConstant.UPDATE.getDesc() : ResultConstant.UPDATEFAILED.getDesc();
    }

    @GetMapping("list")
    List<DepDto> getDepList(@RequestBody @Valid DepDto depDto){
        return depDtoService.list(depDto);
    }

    @GetMapping("page")
    Page<DepDto> getDepPage(@RequestBody @Valid DepDto depDto){
        return depDtoService.page(depDto);
    }

    @GetMapping("{id}")
    DepDto getDepOne(@PathVariable("id") @NotNull Long id, @RequestParam(required = false) String depNo, @RequestParam(required = false) String depName){
        return depDtoService.findDepOne(DepDto.builder().id(id).depNo(depNo).depName(depName).build());
    }
}
