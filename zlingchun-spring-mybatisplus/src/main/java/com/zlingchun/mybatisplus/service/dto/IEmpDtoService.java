package com.zlingchun.mybatisplus.service.dto;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.dto.EmpDto;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
public abstract class IEmpDtoService implements BaseService<EmpDto> {

    /**
     * 保存
     * @param empDto
     * @return
     */
    public abstract boolean save(EmpDto empDto);

    /**
     * 删除
     * @param empDto
     * @return
     */
    public abstract boolean remove(EmpDto empDto);

    /**
     * 根据id删除
     * @param id
     * @return
     */
    public abstract boolean remove(Long id);

    /**
     * 更新
     *
     * @param id
     * @param empDto
     * @return
     */
    public abstract boolean update(Long id, EmpDto empDto);

    /**
     * 分页查询,带关联表查询
     * @param empDto
     * @return
     */
    public abstract Page<EmpDto> findEmpPage(EmpDto empDto);

    /**
     * 查询集合,带关联表查询
     * @param empDto
     * @return
     */
    public abstract List<EmpDto> findEmpList(EmpDto empDto);

    /**
     * 根据唯一字段查询唯一员工
     * @param id
     * @return
     */
    public abstract EmpDto findEmpOne(Long id, String empNo, String empPhone);

    /**
     * 查询单个Emp,不做关联查询
     * @param id
     * @param empNo
     * @param empPhone
     * @return
     */
    public abstract EmpDto findOnlyEmpOne(Long id, String empNo, String empPhone);

    /**
     * 查询Emp List, 不做关联查询
     * @param empDto
     * @return
     */
    public abstract List<EmpDto> findOnlyEmpList(EmpDto empDto);

    /**
     * 分页查询, 不做关联查询
     * @param empDto
     * @return
     */
    public abstract Page<EmpDto> findOnlyEmpPage(EmpDto empDto);
}
