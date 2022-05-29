package com.zlingchun.jdbc.utils;

import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

/**
 * @author achun
 * @create 2022/5/29
 * @description 利用hutool封装的加解密工具，以AES对称加密算法为例
 */
@Slf4j
public final class EncryptorUtils {

    public static String secretKey;

    static {
        secretKey = HexUtil.encodeHexStr(SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded());
    }

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    @SneakyThrows
    public static String encode(String plaintext){
        return EncryptorUtils.encode(secretKey, plaintext);
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    @SneakyThrows
    public static String decode(String ciphertext){
        return EncryptorUtils.decode(secretKey, ciphertext);
    }

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    @SneakyThrows
    public static String encode(String secretKey, String plaintext){
        if(!StringUtils.hasLength(secretKey)){
            secretKey = EncryptorUtils.secretKey;
        }
        log.debug("secretKey-->" + secretKey);
        log.debug("明文字符串：" + plaintext);
        byte[] key = HexUtil.decodeHex(secretKey.toCharArray());
        String ciphertext =  SecureUtil.aes(key).encryptHex(plaintext);
        log.debug("加密后字符串：" + ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    @SneakyThrows
    public static String decode(String secretKey, String ciphertext){
        if(!StringUtils.hasLength(secretKey)){
            secretKey = EncryptorUtils.secretKey;
        }
        log.debug("secretKey-->" + secretKey);
        log.debug("加密字符串：" + ciphertext);
        byte[] key = HexUtil.decodeHex(secretKey.toCharArray());
        String plaintext = SecureUtil.aes(key).decryptStr(ciphertext);
        log.debug("解密后的字符串：" + plaintext);
        return plaintext;
    }

}