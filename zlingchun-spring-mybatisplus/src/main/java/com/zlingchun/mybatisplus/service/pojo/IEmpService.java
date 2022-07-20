package com.zlingchun.mybatisplus.service.pojo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zlingchun.mybatisplus.doman.pojo.Emp;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
public interface IEmpService extends IService<Emp> {

    Page<Emp> selectEmpPage(Emp emp, Integer pageSize, Integer pageNum);

    List<Emp> selectEmpList(Page<Emp> page, Emp emp);

    Emp selectEmpOne(Emp emp);
}