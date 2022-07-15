package com.zlingchun.mybatisplus.service;

import java.util.List;

/**
 * @author achun
 * @create 2022/7/1
 * @description descrip
 */
public interface BaseService<T> {

    int saveBatch(List<T> data);
}
