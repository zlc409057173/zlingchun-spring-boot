package com.zlingchun.mybatis.service;

import com.zlingchun.mybatis.entity.pojo.Dep;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/1
 * @description descrip
 */
public abstract class DepService implements BaseService<Dep>{

    /**
     * 判断是否已经存在，存在就更新，不存在就新增
     * @param dep
     * @return
     */
    public abstract Dep exit(Dep dep);

    /**
     * 新增部门
     * @param dep
     * @return
     */
    public abstract int save(Dep dep);

    /**
     * 查询唯一，必须传入id或名称，否则不查询
     * @param record
     * @return
     */
    public abstract Dep findPrimary(Dep record);

    public abstract int modify(Dep record);

    public abstract int remove(Dep record);

    public abstract List<Dep> findSelective(Dep record);

    public abstract List<Dep> findSelectiveLinkEmps(Dep record);
}
