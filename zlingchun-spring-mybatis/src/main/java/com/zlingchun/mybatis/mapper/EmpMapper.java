package com.zlingchun.mybatis.mapper;

import com.zlingchun.mybatis.entity.po.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmpMapper {

    int insertSelective(Emp record);

    int batchInsert(List<Emp> emps);

    int deleteSelective(Emp record);

    int deletePrimarykey(@Param("id") Long id);

    int batchDelete(@Param("ids") List<Long> ids);

    int updateSelective(Emp record);

    Emp selectPrimarykey(@Param("id") Long id);

    List<Emp> selectForeignkey(@Param("id") Long did);

    List<Emp> selectSelective(Emp record);

    List<Emp> selectSelective2(Emp record);
}