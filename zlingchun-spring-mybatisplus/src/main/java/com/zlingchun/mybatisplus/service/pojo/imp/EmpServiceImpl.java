package com.zlingchun.mybatisplus.service.pojo.imp;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zlingchun.mybatisplus.doman.pojo.Emp;
import com.zlingchun.mybatisplus.mapper.EmpMapper;
import com.zlingchun.mybatisplus.service.pojo.IEmpService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;

/**
 * @author achun
 * @create 2022/7/15
 * @description descrip
 */
@Service
public class EmpServiceImpl extends ServiceImpl<EmpMapper, Emp> implements IEmpService {

    @Resource
    EmpMapper empMapper;

    /**
     * 分页查询
     * @param emp
     * @param pageNum
     * @param pageSize
     * @return
     */
    @Override
    public Page<Emp> selectEmpPage(Emp emp, Integer pageNum, Integer pageSize){
        Page<Emp> page = null;
        if(Objects.nonNull(pageNum) && Objects.nonNull(pageSize)){
            page = new Page<>(pageNum, pageSize);
        }
        List<Emp> emps = this.selectEmpList(page, emp);
        page.setRecords(emps);
        return page;
    }

    /**
     * 查询list
     * @param page
     * @param emp
     * @return
     */
    @Override
    public List<Emp> selectEmpList(Page<Emp> page, Emp emp){
        return empMapper.selectEmpList(page, emp);
    }

    /**
     * 查询员工，唯一字段查询
     * @param emp
     * @return
     */
    @Override
    public Emp selectEmpOne(Emp emp){
        return empMapper.selectEmpOne(emp);
    }
}
