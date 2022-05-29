package com.zlingchun.jdbc.properties;

import com.zlingchun.jdbc.utils.EncryptorUtils;
import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
@Configuration
@ConfigurationProperties(prefix = "custom.encrypt")
@Data
@ToString  //toString方法
@AllArgsConstructor //全参构造器
@NoArgsConstructor  //无参构造器
@Builder  //快速创建对象方法
public class CustomEncryptProperties {

    private boolean enabled = false;
    private String secretKey = EncryptorUtils.secretKey;
}
