package com.zlingchun.jdbc.dao.impl;

import com.zlingchun.jdbc.dao.UserDao;
import com.zlingchun.jdbc.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@Slf4j
@Repository
public class IUserDao implements UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    String insertSQL = "insert into user(id, age, birth, password, realname, username) values (?, ?, ?, ?, ?, ?)";

    String selectSQL = "select id,age,birth,password,realname,username from user where 1 = 1";

    @Override
    public int insert(User user) {
        Object[] objs = {user.getId(), user.getAge(), user.getBirth(), user.getPassword(), user.getRealname(), user.getUsername()};
        return jdbcTemplate.update(insertSQL, objs);
    }

    @Deprecated
    @Override
    /**
     * 主键非自动增长的表不能返回主键id
     */
    public Long insertReturnKey(User user) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        Integer update = jdbcTemplate.update(con -> {
            //在使用Statement.RETURN_GENERATED_KEYS时，需要特别注意，被操作的数据表主键id必须设置AUTO_INCREMENT属性
            PreparedStatement ps = con.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS);
            this.baseInsetSql(ps, user);
            return ps;
        }, keyHolder);
        //主键非自动增长的表不能返回主键id
        //long generatedKey = keyHolder.getKey().longValue();
        return update.longValue();
    }

    @Override
    public int batchInsert(List<User> users) {
        return jdbcTemplate.batchUpdate(insertSQL, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                baseInsetSql(ps, users.get(i));
            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        }).length;
    }

    /**
     * 防止代码冗余，提取公共设置
     * @param ps
     * @param user
     * @throws SQLException
     */
    private void baseInsetSql(PreparedStatement ps, User user) throws SQLException{
        log.info("插入用户的Id, {}", user.getId());
        ps.setLong(1, user.getId());
        ps.setInt(2, user.getAge());
        ps.setTimestamp(3, new Timestamp(user.getBirth().getTime()));
        ps.setString(4, user.getPassword());
        ps.setString(5, user.getRealname());
        ps.setString(6, user.getUsername());
    }

    @Override
    public int update(User user) {
        List<Object> argList = new ArrayList<>();
        StringBuffer sql = new StringBuffer("update user set id=? ");
        argList.add(user.getId());
        if(user.getAge() > 0){
            sql.append(", age = ?");
            argList.add(user.getAge());
        }
        if(!ObjectUtils.isEmpty(user.getBirth())){
            sql.append(", birth = ?");
            argList.add(user.getBirth());
        }
        if(StringUtils.hasText(user.getUsername())){
            sql.append(", username = ?");
            argList.add(user.getUsername());
        }
        if(StringUtils.hasText(user.getRealname())){
            sql.append(", realname = ?");
            argList.add(user.getRealname());
        }
        sql.append(" where 1=1");
        if(user.getId() != null){
            sql.append(" and id = ?");
            argList.add(user.getId());
        }
        return jdbcTemplate.update(sql.toString(), argList.toArray());
    }

    @Override
    public int batchUpdate(List<User> users) {
        String sql = "update user set age = ?, birth = ?, username = ?, realname = ?, password = ? where 1=1 and id = ?";

        return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setInt(1, users.get(i).getAge());
                ps.setTimestamp(2, new Timestamp(users.get(i).getBirth().getTime()));
                ps.setString(3, users.get(i).getUsername());
                ps.setString(4, users.get(i).getRealname());
                ps.setString(5, users.get(i).getPassword());
                ps.setLong(6, users.get(i).getId());

            }

            @Override
            public int getBatchSize() {
                return users.size();
            }
        }).length;
    }

    @Override
    public int delete(Long id) {
        String sql = "delete from user where id = ?";
        return jdbcTemplate.update(sql,id);
    }

    @Override
    public int deleteALL() {
        String sql = "delete from user";
        return jdbcTemplate.update(sql);
    }

    @Override
    public int delete(User user) {
        List<Object> argList = new ArrayList<>();
        String delSql = "delete from user where 1=1";
        String sql = this.getWhereCondition(user, argList, delSql);
        return jdbcTemplate.update(sql, argList.toArray());
    }

    @Override
    public int bathcDelete(Long[] ids) {
        String delSql = "delete from user where 1=1 and id = ?";
        return jdbcTemplate.batchUpdate(delSql, new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                ps.setLong(1, ids[i]);
            }

            @Override
            public int getBatchSize() {
                return ids.length;
            }
        }).length;
    }

    @Override
    public int selectCount(User user) {
        String sql = "select count(1) from user where id = ?";
        return jdbcTemplate.queryForObject(sql, Integer.class, user.getId());
    }

    @Override
    public User select(User user) {
        List<Object> argList = new ArrayList<>();
        String sql = this.getWhereCondition(user, argList, selectSQL);
        try {
            return jdbcTemplate.queryForObject(sql, (rs, rowNum) -> {
                return baseSelectSql(rs);
            }, argList.toArray());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    @Override
    public List<User> selectList(User user) {
        List<Object> argList = new ArrayList<>();
        String sql = this.getWhereCondition(user, argList, selectSQL);
        try {
            return jdbcTemplate.query(sql, (rs, rowNum) -> {
                return baseSelectSql(rs);
            }, argList.toArray());
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    /**
     * 组装查询条件
     * @param user
     * @param argList
     * @return
     */
    private String getWhereCondition(User user, List<Object> argList, String baseSql){
        StringBuffer sql = new StringBuffer(baseSql);
        if(user.getId() != null){
            sql.append(" and id = ?");
            argList.add(user.getId());
        }
        if(user.getAge() > 0){
            sql.append(" and age = ?");
            argList.add(user.getAge());
        }
        if(!ObjectUtils.isEmpty(user.getBirth())){
            sql.append(" and birth >= ?");
            argList.add(user.getBirth());
        }
        if(StringUtils.hasText(user.getUsername())){
            sql.append(" and username like concat('%', ? ,'%')");
            argList.add(user.getUsername());
        }
        if(StringUtils.hasText(user.getRealname())){
            sql.append(" and realname = ?");
            argList.add(user.getRealname());
        }
        return sql.toString();
    }
    /**
     * 防止代码冗余，提取公共设置r
     * @param rs
     * @throws SQLException
     */
    private User baseSelectSql(ResultSet rs) throws SQLException{
        return User.builder().id(rs.getLong("id"))
                .age(rs.getInt("age"))
                .birth(rs.getDate("birth"))
                .password(rs.getString("password"))
                .realname(rs.getString("realname"))
                .username(rs.getString("username"))
                .build();
    }

}
