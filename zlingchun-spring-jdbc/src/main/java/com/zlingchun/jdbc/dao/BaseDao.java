package com.zlingchun.jdbc.dao;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
public interface BaseDao<T> {
    /**
     * 添加，返回受影响的行数
     * @param t
     * @return
     */
    int insert(T t);

    /**
     * 添加，返回主键
     * @param t
     * @return
     */
    Long insertReturnKey(T t);

    /**
     * 批量添加，返回受影响的行数
     * @param ts
     * @return
     */
    int batchInsert(List<T> ts);

    /**
     * 修改，返回受影响的行数
     * @param t
     * @return
     */
    int update(T t);

    /**
     * 批量修改，返回受影响的行数
     * @param ts
     * @return
     */
    int batchUpdate(List<T> ts);

    /**
     * 根据id删除，返回受影响的行数
     * @param id
     * @return
     */
    int delete(Long id);

    /**
     * 删除，返回受影响的行数
     * @param t
     * @return
     */
    int delete(T t);

    /**
     * 批量删除，返回受影响的行数
     * @return
     */
    int bathcDelete(Long[] ids);

    /**
     * 查询指定的总记录，返回总记录数
     * @param t
     * @return
     */
    int selectCount(T t);

    /**
     * 查询指定详情，返回账号对象
     * @param t
     * @return
     */
    T select(T t);

    /**
     * 多条件查询指定列表，返回集合
     * @param t
     * @return
     */
    List<T> selectList(T t);

    int deleteALL();
}
