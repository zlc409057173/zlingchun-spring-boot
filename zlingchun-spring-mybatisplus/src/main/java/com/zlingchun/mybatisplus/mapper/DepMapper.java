package com.zlingchun.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import org.apache.ibatis.annotations.Param;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
public interface DepMapper extends BaseMapper<Dep> {

    Dep selectByDepId(@Param("did") Long id);

}
