package com.zlingchun.mybatis.service.impl;

import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.vo.esayExcel.read.ReaderData;
import com.zlingchun.mybatis.service.BaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
@Slf4j
@Service
public class ReaderDataServiceImp implements BaseService<ReaderData> {

    @Override
    public int saveBatch(List<ReaderData> readerDatas) {
        log.info("{}条数据，开始存储数据库！", readerDatas.size());
        log.info("数据集合，{}", JSON.toJSONString(readerDatas));
        log.info("存储数据库成功！");
        return readerDatas.size();
    }
}
