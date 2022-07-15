package com.zlingchun.mybatisplus.handler.mybatisplus;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j //日志
@Component//以组件的形式把这个处理器注册到IOC容器中
public class MyMetaObjectHandler implements MetaObjectHandler {
    //插入时启动  第三个参数 LocalDateTime 一定要和 createTime成员变量的值的类型一致，不然是null 如果是date就都设置date
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        //如果属性有值则不覆盖,如果填充值为null则不填充
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        //直接调用setFieldValByName方法，跳过null判断，强制更新updateTime为当前时间
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        this.strictInsertFill(metaObject, "version", Integer.class, 0);
    }
    //更新时候启动
    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.setFieldValByName("updateTime", LocalDateTime.now(), metaObject);//
    }
}