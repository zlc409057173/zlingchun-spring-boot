package com.zlingchun.mybatis.mapper;

import com.zlingchun.mybatis.entity.po.Dep;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DepMapper {

    int insertSelective(Dep record);

    List<Dep> selectSelective(Dep record);

    Dep selectPrimarykey(@Param("did") Integer did);

    Dep selectEmps(@Param("did") Integer did);
}