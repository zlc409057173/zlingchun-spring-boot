package com.zlingchun.mybatisplus.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zlingchun.mybatisplus.doman.pojo.Customer;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
public interface CustomerMapper extends BaseMapper<Customer> {

    List<Customer> selectByEmpId(@Param("eid") Long id);

//    boolean insertBatch(@Param("custs") List<Customer> customers);

}
