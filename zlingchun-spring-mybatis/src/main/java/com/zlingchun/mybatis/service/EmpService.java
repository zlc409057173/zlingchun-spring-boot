package com.zlingchun.mybatis.service;

import com.zlingchun.mybatis.entity.po.Emp;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
public abstract class EmpService implements BaseService<Emp> {

    /**
     * 新增部门
     * @param emp
     * @return
     */
    public abstract int save(Emp emp);

    /**
     * 判断是否存在，存在更新，不存在新增
     * @param record
     * @return
     */
    public abstract Emp exit(Emp record);
    /**
     * 查询唯一
     * @param record
     * @return
     */
    public abstract Emp findPrimary(Emp record);

    public abstract int modify(Emp record);

    public abstract int remove(Emp record);

    public abstract List<Emp> findSelective(Emp record);

    public abstract List<Emp> selectSelectiveJoinDep(Emp record);

}
