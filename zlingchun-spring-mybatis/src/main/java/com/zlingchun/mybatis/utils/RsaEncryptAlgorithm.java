package com.zlingchun.mybatis.utils;

import com.cxytiandi.encrypt.algorithm.EncryptAlgorithm;
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
public class RsaEncryptAlgorithm implements EncryptAlgorithm {
    private static Map<String, Object> map = new HashMap<>(2);

    public RsaEncryptAlgorithm(){
        if(CollectionUtils.isEmpty(map)){
            map = RSAUtils.init();
        }
    }

    @Override
    public String encrypt(String content, String encryptKey) throws Exception {
        return RSAUtils.encryptByPublic(content, RSAUtils.getPublicKey(map));
    }

    @Override
    public String decrypt(String encryptStr, String decryptKey) throws Exception {
        return RSAUtils.decryptByPrivate(encryptStr, RSAUtils.getPrivateKey(map));
    }
}
