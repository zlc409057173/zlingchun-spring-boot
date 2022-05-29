package com.zlingchun.jdbc.processor;

import com.alibaba.druid.pool.DruidDataSource;
import com.zlingchun.jdbc.properties.CustomEncryptProperties;
import com.zlingchun.jdbc.utils.EncryptorUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.stereotype.Component;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
@Component
public class DruidDataSourceEncyptBeanPostProcessor implements BeanPostProcessor {

    private CustomEncryptProperties customEncryptProperties;

    private DataSourceProperties dataSourceProperties;

    public DruidDataSourceEncyptBeanPostProcessor(CustomEncryptProperties customEncryptProperties, DataSourceProperties dataSourceProperties) {
        this.customEncryptProperties = customEncryptProperties;
        this.dataSourceProperties = dataSourceProperties;
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if(bean instanceof DruidDataSource){
            if(customEncryptProperties.isEnabled()){
                DruidDataSource druidDataSource = (DruidDataSource)bean;
                String username = dataSourceProperties.getUsername();
                druidDataSource.setUsername(EncryptorUtils.decode(customEncryptProperties.getSecretKey(),username));
                String password = dataSourceProperties.getPassword();
                druidDataSource.setPassword(EncryptorUtils.decode(customEncryptProperties.getSecretKey(),password));
                String url = dataSourceProperties.getUrl();
                druidDataSource.setUrl(EncryptorUtils.decode(customEncryptProperties.getSecretKey(),url));
            }
        }
        return bean;
    }
}
