package com.zlingchun.jpa.utils;

import com.alibaba.druid.filter.config.ConfigTools;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
@Slf4j
public class DruidEncryptorUtils {

    /**
     * alibaba druid加解密规则：
     * 明文密码+私钥(privateKey)加密=加密密码
     * 加密密码+公钥(publicKey)解密=明文密码
     */
    private static String privateKey;
    private static String publicKey;

    static {
        try {
            String[] keyPair = ConfigTools.genKeyPair(512);
            privateKey = keyPair[0];
            log.info(String.format("privateKey-->%s",privateKey));
            publicKey = keyPair[1];
            log.info(String.format("publicKey-->%s",publicKey));
        } catch (NoSuchProviderException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    @SneakyThrows
    static String encode(String plaintext){
        log.info("明文字符串：" + plaintext);
        String ciphertext = ConfigTools.encrypt(privateKey,plaintext);
        log.info("加密后字符串：" + ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    @SneakyThrows
    static String decode(String ciphertext){
        log.info("加密字符串：" + ciphertext);
        String plaintext = ConfigTools.decrypt(publicKey,ciphertext);
        log.info("解密后的字符串：" + plaintext);

        return plaintext;
    }
}
