package com.zlingchun.mybatis.service;

import com.zlingchun.mybatis.entity.dto.EmpDto;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
public abstract class EmpService implements BaseService<EmpDto> {

    /**
     * 新增部门
     * @param empDto
     * @return
     */
    public abstract int save(EmpDto empDto);

    /**
     * 判断是否存在，存在更新，不存在新增
     * @param record
     * @return
     */
    public abstract EmpDto exit(EmpDto record);
    /**
     * 查询唯一
     * @param record
     * @return
     */
    public abstract EmpDto findPrimary(EmpDto record);

    public abstract EmpDto findPrimarykey(Long eid);

    public abstract int modify(EmpDto record);

    public abstract int remove(Long eid);

    public abstract List<EmpDto> findSelective(EmpDto record);

    public abstract List<EmpDto> findSelectiveJoinDep(EmpDto record);

}
