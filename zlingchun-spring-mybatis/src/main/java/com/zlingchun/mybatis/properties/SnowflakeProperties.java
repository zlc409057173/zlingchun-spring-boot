package com.zlingchun.mybatis.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author achun
 * @create 2022/6/12
 * @description descrip
 */
@Data
@ConfigurationProperties(prefix = "custom.snowflake")
public class SnowflakeProperties {
    private long machineId;
    private long dataCenterId;
}