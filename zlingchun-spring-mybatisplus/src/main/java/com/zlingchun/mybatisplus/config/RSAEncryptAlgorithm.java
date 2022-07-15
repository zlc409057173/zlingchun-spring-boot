package com.zlingchun.mybatisplus.config;

import com.zlingchun.mybatisplus.util.security.RSAUtils;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author achun
 * @create 2022/6/14
 * @description descrip
 */
@Component
public class RSAEncryptAlgorithm {
    private static Map<String, Object> map = new HashMap<>(2);

    public RSAEncryptAlgorithm(){
        if(CollectionUtils.isEmpty(map)){
            map = RSAUtils.init();
        }
    }

    @SneakyThrows
    public String encrypt(String content, String encryptKey) {
        return RSAUtils.encryptByPublic(content, RSAUtils.getPublicKey(map));
    }

    @SneakyThrows
    public String decrypt(String encryptStr, String decryptKey) {
        return RSAUtils.decryptByPrivate(encryptStr, RSAUtils.getPrivateKey(map));
    }
}
