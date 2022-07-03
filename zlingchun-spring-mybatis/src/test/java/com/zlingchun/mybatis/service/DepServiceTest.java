package com.zlingchun.mybatis.service;

import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.zlingchun.mybatis.entity.po.Dep;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/7/2
 * @description descrip
 */
@SpringBootTest
@Slf4j
@DisplayName("部门Service测试")
public class DepServiceTest {
    @Resource
    private DepService depService;

    private static final String[] depNames = {"总经办", "研发", "测试", "产品"};

    private static List<Dep> deps = ListUtils.newArrayList();

    @BeforeAll
    static void baseData() {
        for (int i = 0; i < depNames.length; i++) {
            Dep dep = Dep.builder().depName(depNames[i]).build();
            deps.add(dep);
        }
    }

    @Test
    @Order(value = Integer.MAX_VALUE)
    @DisplayName("批量新增")
    void saveBatch(){
        int saveBatch = depService.saveBatch(deps);
        Assertions.assertEquals(deps.size(), saveBatch);
    }

    @Test
    @Order(value = Integer.MAX_VALUE-2)
    @DisplayName("存在更新，不存在新增")
    void exit(){
        deps.forEach(dep -> {
            Dep exit = depService.exit(dep);
            log.info("DepId={} : {}", exit.getDid() ,JSON.toJSONString(exit));
        });
    }

    @Test
    @Order(value = Integer.MAX_VALUE-1)
    @DisplayName("单条新增")
    void save(){
        int save = depService.save(Dep.builder().depName("经理").build());
        Assertions.assertEquals(deps.size(), save);
    }

    @Test
    @Order(value = Integer.MAX_VALUE-3)
    @DisplayName("单条更新")
    @Transactional
    void modify(){
        Dep dep = depService.findPrimary(Dep.builder().depName("经理").build());
        if(ObjectUtils.isEmpty(dep)) return;
        dep.setDepName("总经办");
        Dep primary = depService.findPrimary(Dep.builder().depName("总经办").build());
        if(ObjectUtils.isEmpty(primary)){
            int modify = depService.modify(dep);
            Assertions.assertEquals(1, modify);
        }
    }

    @Test
    @Order(value = Integer.MAX_VALUE-4)
    @DisplayName("单条删除")
    @Transactional
    void remove(){
        Dep dep = depService.findPrimary(Dep.builder().depName("经理").build());
        int remove = depService.remove(dep);
        Assertions.assertEquals(1, remove);
    }

    @Test
    @Order(value = Integer.MAX_VALUE-5)
    @DisplayName("模糊查询")
    void findSelective(){
        Dep dep = Dep.builder().build();
        dep.setPageNum(0);
        dep.setPageSize(2);
        List<Dep> deps = depService.findSelective(dep);
        log.info(JSON.toJSONString(deps));
    }

    @Test
    @Order(value = Integer.MAX_VALUE-6)
    @DisplayName("关联模糊查询")
    void findSelectiveLinkEmps(){
        Dep dep = Dep.builder().build();
        dep.setPageNum(0);
        dep.setPageSize(2);
        List<Dep> deps = depService.findSelectiveLinkEmps(dep);
        log.info(JSON.toJSONString(deps));
    }

}
