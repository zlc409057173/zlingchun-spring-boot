package com.zlingchun.mybatis.mapper;

import com.zlingchun.mybatis.entity.po.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * @author achun
 * @create 2022/6/15
 * @description descrip
 */
@Mapper
public interface UserMapper {

    List<User> findAll();

    @Select("select * from t_user where id = #{id}")
    User findById(Integer id);

    int add(User user);

    @Insert("insert into t_user(name, real_name, birth, age, money) values (#{name}, #{realName}, #{birth}, #{age}, #{money})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int addUser(User user);
}
