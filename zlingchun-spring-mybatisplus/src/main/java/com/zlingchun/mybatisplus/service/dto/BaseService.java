package com.zlingchun.mybatisplus.service.dto;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/1
 * @description descrip 如果需要用到easy excel导入功能则需要继承此接口
 */
public interface BaseService<T> {

    boolean saveBatch(List<T> data);
}
