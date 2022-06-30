package com.zlingchun.mybatis.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.metadata.data.ReadCellData;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ConverterUtils;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.zlingchun.mybatis.entity.po.Emp;
import com.zlingchun.mybatis.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
// 有个很重要的点 EmpListener 不能被spring管理，要每次读取excel都要new,然后里面用到spring可以构造方法传进去
@Slf4j
public class EmpListener implements ReadListener<Emp> {
    EmpService dataService;

    public EmpListener() {
    }

    public EmpListener(EmpService dataService) {
        this.dataService = dataService;
    }

    /**
      * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;
    /**
      * 缓存的数据
     */
    private List<Emp> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);

    /**
     * 这个每一条数据解析都会来调用
     *
     * @param data    one row value. Is is same as {@link AnalysisContext#readRowHolder()}
     * @param context
     */
    @Override
    public void invoke(Emp data, AnalysisContext context) {
        log.info("解析到一条数据:{}", JSON.toJSONString(data));
        cachedDataList.add(data);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            dataService.batchSave(cachedDataList);
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    /**
     * 所有数据解析完成了 都会来调用
     *
     */
    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        if(!CollectionUtils.isEmpty(cachedDataList)){
            dataService.batchSave(cachedDataList);
        }
        log.info("所有数据解析完成！");
    }

    @Override
    public void invokeHead(Map<Integer, ReadCellData<?>> headMap, AnalysisContext context) {
        Map<Integer, String> integerStringMap = ConverterUtils.convertToStringMap(headMap, context);
        log.info("解析到一条头数据:{}", JSON.toJSONString(integerStringMap));
    }
}
