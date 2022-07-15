package com.zlingchun.mybatisplus.mapper;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.excel.util.ListUtils;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zlingchun.mybatisplus.doman.pojo.Dep;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author achun
 * @create 2022/7/14
 * @description descrip
 */
@Slf4j
@SpringBootTest
public class DepMapperTest {

    @Resource
    DepMapper depMapper;

    private static final String[] depNames = {"总经办", "研发", "测试", "产品"};

    private static List<Dep> deps = ListUtils.newArrayList();

    @BeforeEach
    @Disabled
    public void baseData() {
        Snowflake snowflake = new Snowflake(1, 1);
        for (int i = 0; i < depNames.length; i++) {
            Dep dep = Dep.builder().depName(depNames[i]).depNo(snowflake.nextIdStr().substring(8)).build();
            dep.setCreateBy("mapper");
            dep.setUpdateBy("mapper");
            deps.add(dep);
        }
    }

    @Test
    void insert(){
        deps.forEach(dep -> {
            LambdaQueryWrapper<Dep> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.like(StringUtils.isNotEmpty(dep.getDepName()), Dep::getDepName, dep.getDepName());
            if(!depMapper.exists(queryWrapper)) {
                int insert = depMapper.insert(dep);
                Assertions.assertEquals(1, insert);
            }
        });
    }

    @Test
    void exists(){
        String depName = "经";
        // 方式一：
//        QueryWrapper<Dep> queryWrapper = new QueryWrapper<>();
//        queryWrapper.like(StringUtils.isNotEmpty(depName), "dep_name", depName);
        // 方式二：
        LambdaQueryWrapper<Dep> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(depName), Dep::getDepName, depName);
        boolean exists = depMapper.exists(queryWrapper);
        Assertions.assertTrue(exists);
    }

    @Test
    void updateById(){
        String depName = "经";
        // 方式一：
        QueryWrapper<Dep> queryWrapper = new QueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(depName), "dep_name", depName);
        Dep dep = depMapper.selectOne(queryWrapper);
        dep.setDepName("编辑");
        int update = depMapper.updateById(dep);
        Assertions.assertEquals(1, update);
    }

    @Test
    void update(){
        String depName = "研发";
        // 方式一：
        LambdaUpdateWrapper<Dep> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(StringUtils.isNotEmpty(depName), Dep::getDepName, depName);
        updateWrapper.set(StringUtils.isNotEmpty(depName), Dep::getDepName, "总经办");
        int update = depMapper.update(null, updateWrapper);
        Assertions.assertEquals(1, update);
    }

    @Test
    void selectById(){
        Dep dep = depMapper.selectById(1);
        log.info(JSON.toJSONString(dep));
    }

    @Test
    void selectBatchIds(){
        List<Dep> deps = depMapper.selectBatchIds(Arrays.asList(1, 2));
        log.info(JSON.toJSONString(deps));
    }

    @Test
    void selectByMap(){
        Map<String, Object> columnMap = new HashMap<>(6);
        columnMap.put("dep_name", "总经办");
        List<Dep> deps = depMapper.selectByMap(columnMap);
        log.info(JSON.toJSONString(deps));
    }

    @Test
    void deleteById(){
        Dep dep = depMapper.selectById(1);
        log.info(JSON.toJSONString(dep));
        int delete = depMapper.deleteById(dep);
        Assertions.assertEquals(1, delete);
    }

    @Test
    void selectPage(){
        Page<Dep> page = new Page<>(1, 2);
        depMapper.selectPage(page, null);
        log.info(JSON.toJSONString(page));
    }

}
