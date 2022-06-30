package com.zlingchun.mybatis.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.vo.esayExcel.read.po.ReaderData;
import com.zlingchun.mybatis.mapper.ReaderDataMapper;
import com.zlingchun.mybatis.service.ReaderDataService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
@Slf4j
@Service
public class ReaderDataServiceImp implements ReaderDataService {

    @Resource
    private ReaderDataMapper dataMapper;

    @Override
    public int batchSave(List<ReaderData> readerDatas) {
        log.info("{}条数据，开始存储数据库！", readerDatas.size());
        log.info("数据集合，{}", JSON.toJSONString(readerDatas));
        int count = dataMapper.batchInsert(readerDatas);
        log.info("存储数据库成功！");
        return count;
    }
}
