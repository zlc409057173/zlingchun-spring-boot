package com.zlingchun.mybatis.mapper;

import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson.JSON;
import com.zlingchun.mybatis.entity.pojo.Dep;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author achun
 * @create 2022/6/28
 * @description descrip
 */
@SpringBootTest
@Slf4j
public class DepMapperTest {

    @Resource
    private DepMapper depMapper;

    private static final String[] depNames = {"总经办", "研发", "测试", "产品"};

    private static List<Dep> deps = ListUtils.newArrayList();

    @BeforeEach
    @Disabled
    public void baseData() {
        for (int i = 0; i < depNames.length; i++) {
            Dep dep = Dep.builder().depName(depNames[i]).build();
            dep.setCreateBy("mapper");
            dep.setUpdateBy("mapper");
            deps.add(dep);
        }
    }

    @Test
    void insert(){
        Dep dep = deps.get(0);
        int insertSelective = depMapper.insertSelective(dep);
        Assertions.assertEquals(1, insertSelective);
    }

    @Test
    void insertBatch(){
        int insertBatch = depMapper.insertBatch(deps);
        Assertions.assertEquals(deps.size(), insertBatch);
    }

    @Test
    void updateSelective(){
        Dep dep = depMapper.selectPrimary(Dep.builder().depName("董事").build());
        dep.setDepName("经理");
        dep.setUpdateBy("update");
        int updateSelective = depMapper.updateSelective(dep);
        Assertions.assertEquals(1, updateSelective);
    }

    @Test
    void deleteSelective(){
        Dep dep = depMapper.selectPrimary(Dep.builder().depName("测试").build());
        int deleteSelective = depMapper.deleteSelective(dep);
        Assertions.assertEquals(1, deleteSelective);
    }

    @Test
    void selectPrimarykey(){
        Dep dep = depMapper.selectPrimary(Dep.builder().depName("研发").build());
        Dep selectPrimarykey = depMapper.selectPrimarykey(dep.getDid());
        Assertions.assertEquals("研发", selectPrimarykey.getDepName());
    }

    @Test
    void selectSelective(){
        Dep dep = Dep.builder().depName("产").build();
        dep.setCreateBy("mapper");
        List<Dep> deps = depMapper.selectSelective(dep);
        log.info(JSON.toJSONString(deps));
    }

    @Test
    void selectSelectiveLinkEmps(){
        Dep dep = Dep.builder().build();
        dep.setCreateBy("mapper");
        List<Dep> deps = depMapper.selectSelectiveLinkEmps(dep);
        log.info(JSON.toJSONString(deps));
    }
}
