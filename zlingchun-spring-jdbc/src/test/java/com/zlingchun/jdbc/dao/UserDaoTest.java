package com.zlingchun.jdbc.dao;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.lang.Snowflake;
import com.zlingchun.jdbc.entity.User;
import com.zlingchun.jdbc.utils.EncryptorUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class UserDaoTest{

    @Autowired
    UserDao dao;

    @Autowired
    Snowflake snowflake;

    @Test
    void insert(){
        User user = User.builder().id(snowflake.nextId()).age(30).birth(DateTime.now())
                .password(EncryptorUtils.encode("123456")).realname("张三").username("zs").build();
        log.info("用户Id, {}", user.getId());
        log.info("插入了 {} 条", dao.insert(user));
    }

    @Test
    void isertReturnKey(){
        User user = User.builder().id(snowflake.nextId()).age(20).birth(DateTime.now())
                .password(EncryptorUtils.encode("56789")).realname("李四").username("ls").build();
        log.info("插入了 {} 条", dao.insertReturnKey(user));
    }

    @Test
    void batchInsert() {
        User zs = User.builder().id(snowflake.nextId()).age(30).birth(DateTime.now())
                .password(EncryptorUtils.encode("123456")).realname("张三").username("zs").build();
        User ls = User.builder().id(snowflake.nextId()).age(20).birth(DateTime.now())
                .password(EncryptorUtils.encode("56789")).realname("李四").username("ls").build();
        List<User> users = Arrays.asList(zs, ls);
        log.info("插入了 {} 条", dao.batchInsert(users));
    }

    @Test
    void delete(){
//        log.info("删除了 {} 条",dao.delete(1535859402173714432L));
        User user = User.builder().id(1535882627398111232L).username("lw").realname("李五").build();
        log.info("删除了 {} 条",dao.delete(user));
    }

    @Test
    void deleteALL(){
        log.info("删除了 {} 条",dao.deleteALL());
    }

    @Test
    void bathcDelete() {
        Long[] ids = {1535882627398111232L, 1535882627414888448L};
        log.info("批量删除了 {} 条",dao.bathcDelete(ids));
    }

    @Test
    void update() {
        User user = User.builder().id(1535882627398111232L).username("lw").realname("李五").build();
        log.info("更新了 {} 条",dao.update(user));
    }

    @Test
    void batchUpdate() {
        User user = User.builder().username("s").build();
        List<User> users = dao.selectList(user);
        log.info("查询到的User列表详情, {}", users);
        users.stream().forEach(u -> {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            u.setBirth(DateTime.now());
        });
        log.info("批量更新了 {} 条", dao.batchUpdate(users));
    }

    @Test
    void selectCount() {
        User user = User.builder().id(1535882627398111232L).build();
        log.info("查询到了 {} 条", dao.selectCount(user));
    }

    @Test
    void select() {
        User user = User.builder().id(1535882627398111232L).age(30).build();
        log.info("查询到的User详情, {}", dao.select(user));
    }

    @Test
    void selectList() {
        User user = User.builder().username("s").build();
        log.info("查询到的User列表详情, {}", dao.selectList(user));
    }

}
