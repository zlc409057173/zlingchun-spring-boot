package com.zlingchun.jdbc.utils;

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
//            privateKey = keyPair[0];
            privateKey = "MIIBVAIBADANBgkqhkiG9w0BAQEFAASCAT4wggE6AgEAAkEAhD15RVRCMVj/a70Paj5iaBzTiwO0DsFhPNaaPU5xXNanh359cVULB8GGql4/esbxI5PjefwiBFsaKxVMSNok1QIDAQABAkAii/C/hOiZ/bcUjVqWld+ddVJqcbg7VpexjhrTQ0foSab/X1eO+dYIEukMUanj9HJRx1cWn/brg3cBwNMoFTCtAiEAykkmju4O7x2HtzJ+Lf9QQ0PEuORebMGRu8gQTM7PZgsCIQCnWs7KEayyhE+nb/CaPhvegtNLkeg1K+dXKVnDssvMnwIhAMPHercY9oE8/m4zQ2Y3VbJUc6BS/oSZ7/djkUsVWiuzAiA17T8BQhzCEXyRWbLQIP9d/g4UJdoLeDA9l+eC9hRRxQIgbaY8qCkcEX4jdyk5TQRjl8A0xUxgEdnJD+yszDQT3lc=";
            log.info(String.format("privateKey-->%s",privateKey));
//            publicKey = keyPair[1];
            publicKey = "MFwwDQYJKoZIhvcNAQEBBQADSwAwSAJBAIQ9eUVUQjFY/2u9D2o+Ymgc04sDtA7BYTzWmj1OcVzWp4d+fXFVCwfBhqpeP3rG8SOT43n8IgRbGisVTEjaJNUCAwEAAQ==";
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
