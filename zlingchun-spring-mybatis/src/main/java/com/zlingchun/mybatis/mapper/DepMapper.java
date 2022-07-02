package com.zlingchun.mybatis.mapper;

import com.zlingchun.mybatis.entity.po.Dep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepMapper {

    /**
     * 新增单条
     * @param record
     * @return
     */
    int insertSelective(Dep record);

    /**
     * 批量新增，为了性能考虑最好不要超过100条每批次
     * @param record
     * @return
     */
    int insertBatch(@Param("deps") List<Dep> record);

    /**
     * 单条更新, 只有传入部门id和部门名称时才更新, 否则不更新
     * @param record
     * @return
     */
    int updateSelective(Dep record);

    /**
     * 根据Id或名称删除, 否则不删除
     * @param record
     * @return
     */
    int deleteSelective(Dep record);

    /**
     * 验证唯一性, 通过id或名称, 只有传入id和名称时才能查到结果，否则不能查到结果
     * 插入的时候需要通过该方法验证，如果只要根据id或者名称能查询到记录，就不能插入
     * @param record
     * @return
     */
    Dep selectPrimary(Dep record);

    /**
     * 模糊匹配
     * @param record
     * @return
     */
    List<Dep> selectSelective(Dep record);

    /**
     * 根据Id查询
     * @param did
     * @return
     */
    Dep selectPrimarykey(@Param("did") Integer did);

    /**
     * 级联查询，关联出部门下的员工
     * @param record
     * @return
     */
    List<Dep> selectSelectiveLinkEmps(Dep record);
}