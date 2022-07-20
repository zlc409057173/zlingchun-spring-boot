package com.zlingchun.mybatisplus.service.dto.impl;

import cn.hutool.core.lang.Snowflake;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.zlingchun.mybatisplus.converter.mapstruct.EmpToEmpDto;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import com.zlingchun.mybatisplus.service.dto.IDepDtoService;
import com.zlingchun.mybatisplus.service.pojo.IDepService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author achun
 * @create 2022/7/19
 * @description descrip
 */
@Service
public class DepDtoServiceImpl implements IDepDtoService {

    @Resource
    IDepService depService;

    @Resource
    EmpToEmpDto empToEmpDto;

    @Resource
    Snowflake snowflake;

    /**
     * 获取部门信息：
     *   1.1 Id存在，根据Id查询，
     *       如果部门不存在就新增，新增需确保DepName不为空，并设置DepNo，返回自动生成的Id
     *       如果部门存在就获取部门Id，不做任何改变
     *   1.2 Id不存在，根据DepNo或者DepName查询
     *       如果部门不存在就新增，新增需确保DepName不为空，并设置DepNo，返回自动生成的Id
     *       如果部门存在就获取部门Id，不做任何改变
     * @param depDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean save(DepDto depDto){
        DepDto one = this.findDepOne(depDto);
        if(Objects.nonNull(one)){
            // 存在
            depDto.setId(one.getId());
            return true;
        }
        // 不存在
        if(StringUtils.isEmpty(depDto.getDepName())){
            throw new IllegalArgumentException("Dep Name cannot be empty while create Dep!");
        }
        // 通过雪花算法生成一个数，再截取8位后的数
        depDto.setDepNo(snowflake.nextIdStr().substring(8));
        depDto.setId(null); // 防止新增时传入Id
        Dep dep = empToEmpDto.depDto2Dep(depDto);
        // 新增
        boolean save = depService.save(dep);
        if(save){
            depDto.setId(dep.getId());
            return true;
        }
        return false;
    }

    /**
     * 获取部门信息：
     *   1.1 Id存在，根据Id查询，
     *   1.2 Id不存在，根据DepNo或者DepName查询
     * @param depDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public DepDto findDepOne(DepDto depDto){
        Dep dep = empToEmpDto.depDto2Dep(depDto);
        LambdaQueryWrapper<Dep> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(dep.getId()), Dep::getId, dep.getId())
        .or()
        .eq(StringUtils.isNotBlank(dep.getDepNo()), Dep::getDepNo, dep.getDepNo())
        .or()
        .eq(StringUtils.isNotBlank(dep.getDepName()), Dep::getDepName, dep.getDepName());
        Dep one = depService.getOne(lambdaQueryWrapper);
        return empToEmpDto.dep2DepDto(one);
    }

}
