package com.zlingchun.mybatis.mapper;

import com.zlingchun.mybatis.entity.vo.esayExcel.read.po.ReaderData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/29
 * @description descrip
 */
public interface ReaderDataMapper {

    int insert(ReaderData record);

    int batchInsert(@Param("records") List<ReaderData> records);
}
