package com.zlingchun.mybatis.service;

import com.zlingchun.mybatis.entity.vo.esayExcel.read.po.ReaderData;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/30
 * @description descrip
 */
public interface ReaderDataService {

    int batchSave(List<ReaderData> readerDatas);

}
