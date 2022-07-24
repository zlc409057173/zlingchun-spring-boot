package com.zlingchun.mybatisplus.service.dto.impl;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.converter.mapstruct.DepConvert;
import com.zlingchun.mybatisplus.doman.dto.DepDto;
import com.zlingchun.mybatisplus.doman.dto.DepQueryDto;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import com.zlingchun.mybatisplus.service.dto.IDepDtoService;
import com.zlingchun.mybatisplus.service.dto.IEmpDtoService;
import com.zlingchun.mybatisplus.service.pojo.IDepService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    DepConvert depConvert;

    @Resource
    IEmpDtoService empDtoService;

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
        DepQueryDto depQueryDto = depConvert.depDto2DepQueryDto(depDto);
        DepDto one = this.findDepOne(depQueryDto);
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
        Dep dep = depConvert.depDto2Dep(depDto);
        // 新增
        boolean save = depService.save(dep);
        if(save){
            depDto.setId(dep.getId());
            return true;
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(DepDto depDto) {
        DepQueryDto depQueryDto = depConvert.depDto2DepQueryDto(depDto);
        List<DepDto> depDtos = this.list(depQueryDto);
        if(CollectionUtils.isEmpty(depDtos)){
            throw new IllegalArgumentException("No such Condition's Emp, " + JSON.toJSONString(depDto));
        }
        List<Long> ids = depDtos.stream().map(DepDto::getId).collect(Collectors.toList());
        boolean removeEmp = empDtoService.removeByDepId(ids);
        boolean removeDep = depService.removeBatchByIds(ids, 100);
        return removeEmp && removeDep;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean remove(Long id) {
        DepDto depDto = this.findDepOne(DepQueryDto.builder().id(id).build());
        if(Objects.isNull(depDto)){
            throw new IllegalArgumentException("This Dep has been deleted!");
        }
        boolean removeEmp = empDtoService.removeByDepId(Arrays.asList(id));
        boolean removeDep = depService.removeById(id);
        return removeEmp && removeDep;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public boolean update(Long id, DepDto depDto) {
        DepQueryDto depQueryDto = depConvert.depDto2DepQueryDto(depDto);
        DepDto depOne = this.findDepOne(depQueryDto);
        if(Objects.nonNull(depOne)){
            throw new IllegalArgumentException("You can't update the Dep, The Dep name has existed!");
        }
        LambdaQueryWrapper<Dep> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Dep::getId, id);
        Dep dep = depConvert.depDto2Dep(depDto);
        return depService.update(dep, lambdaQueryWrapper);
    }

    /**
     * 获取部门信息：
     *   1.1 Id存在，根据Id查询，
     *   1.2 Id不存在，根据DepNo或者DepName查询
     * @param depQueryDto
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public DepDto findDepOne(DepQueryDto depQueryDto){
        Dep dep = depConvert.depQueryDto2Dep(depQueryDto);
        LambdaQueryWrapper<Dep> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(dep.getId()), Dep::getId, dep.getId())
                .or()
                .eq(StringUtils.isNotBlank(dep.getDepNo()), Dep::getDepNo, dep.getDepNo())
                .or()
                .eq(StringUtils.isNotBlank(dep.getDepName()), Dep::getDepName, dep.getDepName());
        Dep one = depService.getOne(lambdaQueryWrapper);
        return depConvert.dep2DepDto(one);
    }

    @Override
    public List<DepDto> list(DepQueryDto depQueryDto) {
        Dep dep = depConvert.depQueryDto2Dep(depQueryDto);
        LambdaQueryWrapper<Dep> lambdaQueryWrapper = this.getWrapper(dep);
        List<Dep> deps = depService.list(lambdaQueryWrapper);
        return depConvert.dep2DepDto(deps);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Page<DepDto> page(DepQueryDto depQueryDto) {
        if(Objects.isNull(depQueryDto.getPageNum()) || Objects.isNull(depQueryDto.getPageSize())) {
            throw new IllegalArgumentException("The pageNum and pageSize can't empty while Page look!");
        }
        Dep dep = depConvert.depQueryDto2Dep(depQueryDto);
        LambdaQueryWrapper<Dep> lambdaQueryWrapper = this.getWrapper(dep);
        Page<Dep> page = new Page<>(depQueryDto.getPageNum(), depQueryDto.getPageSize());
        Page<Dep> depPage = depService.page(page, lambdaQueryWrapper);
        return depConvert.dep2DepDto(depPage);
    }

    private LambdaQueryWrapper<Dep> getWrapper(Dep dep){
        LambdaQueryWrapper<Dep> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Objects.nonNull(dep.getId()), Dep::getId, dep.getId())
                .eq(StringUtils.isNotBlank(dep.getDepName()), Dep::getDepName, dep.getDepName())
                .eq(StringUtils.isNotBlank(dep.getDepNo()), Dep::getDepNo, dep.getDepNo());
        return lambdaQueryWrapper;
    }

}
