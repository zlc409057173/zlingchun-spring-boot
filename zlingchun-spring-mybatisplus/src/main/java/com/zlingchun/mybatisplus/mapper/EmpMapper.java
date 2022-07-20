package com.zlingchun.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
public interface EmpMapper extends BaseMapper<Emp> {

    List<Emp> selectEmpList(@Param("page") Page<Emp> page, @Param("em") Emp emp);

    Emp selectEmpOne(@Param("em") Emp emp);

}
