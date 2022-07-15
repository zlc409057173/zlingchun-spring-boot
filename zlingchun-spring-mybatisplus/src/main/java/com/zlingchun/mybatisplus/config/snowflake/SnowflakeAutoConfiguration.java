package com.zlingchun.mybatisplus.config.snowflake;

import cn.hutool.core.lang.Snowflake;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@Configuration
@EnableConfigurationProperties(SnowflakeProperties.class)
public class SnowflakeAutoConfiguration {
    @Autowired
    private SnowflakeProperties properties;

    @Bean
    @ConditionalOnMissingBean
    public Snowflake snowflake() {
        return new Snowflake(properties.getMachineId(), properties.getDataCenterId());
    }
}
