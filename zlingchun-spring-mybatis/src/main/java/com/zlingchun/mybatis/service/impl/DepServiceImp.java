package com.zlingchun.mybatis.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.po.Dep;
import com.zlingchun.mybatis.mapper.DepMapper;
import com.zlingchun.mybatis.service.DepService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/1
 * @description descrip
 */
@Service
@Slf4j
public class DepServiceImp extends DepService {

    @Resource
    DepMapper depMapper;

    /**
     * 批量新增
     * @param data
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int saveBatch(List<Dep> data) {
        if(CollectionUtils.isEmpty(data)) return 0;
        data.forEach(dep -> {
            dep.setCreateBy("serviceSaveBatch");
            dep.setUpdateBy("serviceSaveBatch");
        });
        log.info(JSON.toJSONString(data));
        return depMapper.insertBatch(data);
    }

    /**
     * 单条处理，判断是否存在，存在不做任何处理，不存在新增
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Dep exit(Dep record) {
        Dep dep = this.findPrimary(record);
        log.info("查询的depName : {}", record.getDepName());
        if(ObjectUtils.isEmpty(dep)){
            record.setCreateBy("serviceExit");
            record.setUpdateBy("serviceExit");
            record.setUpdateTime(LocalDateTime.now());
            save(record);
            return record;
        }
        return dep;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int save(Dep dep) {
        dep.setCreateBy("serviceSave");
        dep.setUpdateBy("serviceSave");
        dep.setUpdateTime(LocalDateTime.now());
        return depMapper.insertSelective(dep);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Dep findPrimary(Dep record){
        return depMapper.selectPrimary(record);
    }

    /**
     * 单条更新, 只有传入部门id和部门名称时才更新, 否则不更新
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int modify(Dep record) {
        //这里都有可能传进来的不是Dep对象，而是其他对象，那么这里需要做转换然后再更新
        record.setUpdateBy("serviceUpdate");
        record.setUpdateTime(LocalDateTime.now());
        return depMapper.updateSelective(record);
    }

    /**
     * 单条删除, 只有传入部门id和部门名称时才删除, 否则不删除
     * @param record
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public int remove(Dep record) {
        //这里都有可能传进来的不是Dep对象，而是其他对象，那么这里需要做转换然后再删除
        return depMapper.deleteSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Dep> findSelective(Dep record) {
        return depMapper.selectSelective(record);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Dep> findSelectiveLinkEmps(Dep record) {
        return depMapper.selectSelectiveLinkEmps(record);
    }
}
