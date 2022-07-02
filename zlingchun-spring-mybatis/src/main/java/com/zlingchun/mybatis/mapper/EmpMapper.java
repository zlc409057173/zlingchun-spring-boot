package com.zlingchun.mybatis.mapper;

import com.zlingchun.mybatis.entity.po.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    /**
     * 插入单条
     * @param record
     * @return
     */
    int insertSelective(Emp record);

    /**
     * 批量新增，为了性能考虑最好不要超过100条每批次
     * @param emps
     * @return
     */
    int insertBatch(@Param("emps") List<Emp> emps);

    /**
     * 单条更新, 只有传入id时才更新，否则不更新
     * @param record
     * @return
     */
    int updateSelective(Emp record);

    /**
     * 选择性删除，如果Id不为空就根据Id删除，如果Id为空，就模糊匹配删除
     * @param record
     * @return
     */
    int deleteSelective(Emp record);

    /**
     * 批量删除，传入的数量不要超过1000，为了性能考虑最好每批次100条
     * @param ids
     * @return
     */
    int deleteBatch(@Param("ids") List<Long> ids);

    /**
     * 验证唯一性，通过id或员工号或者手机号查验
     * 插入的时候需要通过该方法验证，如果只要根据id或者名称能查询到记录，就不能插入
     * @param record
     * @return
     */
    Emp selectPrimary(Emp record);

    /**
     * 模糊匹配
     * @param record
     * @return
     */
    List<Emp> selectSelective(Emp record);

    /**
     * 模糊匹配, 关联Dep部门表联合查询
     * @param record
     * @return
     */
    List<Emp> selectSelectiveJoinDep(Emp record);

    /**
     * 根据主键查询
     * @param eid
     * @return
     */
    Emp selectPrimarykey(@Param("eid") Long eid);

    /**
     * 根据外键查询Emp，供Dep关联查询使用
     * @param did
     * @return
     */
    List<Emp> selectForeignkey(@Param("did") Long did);
}