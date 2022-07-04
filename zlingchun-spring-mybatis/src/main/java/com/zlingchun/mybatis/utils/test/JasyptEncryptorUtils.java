package com.zlingchun.mybatis.utils.test;

import lombok.extern.slf4j.Slf4j;
import org.jasypt.properties.PropertyValueEncryptionUtils;
import org.jasypt.util.text.BasicTextEncryptor;

/**
 * @author achun
 * @create 2022/5/29
 * @description descrip
 */
@Slf4j
public final class JasyptEncryptorUtils {

    private static final String salt = "lybgeek";

    private static BasicTextEncryptor basicTextEncryptor = new BasicTextEncryptor();

    static {
        basicTextEncryptor.setPassword(salt);
    }

    private JasyptEncryptorUtils(){}

    /**
     * 明文加密
     * @param plaintext
     * @return
     */
    public static String encode(String plaintext){
        log.info("明文字符串：" + plaintext);
        String ciphertext = basicTextEncryptor.encrypt(plaintext);
        log.info("加密后字符串：" + ciphertext);
        return ciphertext;
    }

    /**
     * 解密
     * @param ciphertext
     * @return
     */
    public static String decode(String ciphertext){
        log.info("加密字符串：" + ciphertext);
        ciphertext = "ENC(" + ciphertext + ")";
        if (PropertyValueEncryptionUtils.isEncryptedValue(ciphertext)){
            String plaintext = PropertyValueEncryptionUtils.decrypt(ciphertext,basicTextEncryptor);
            log.info("解密后的字符串：" + plaintext);
            return plaintext;
        }
        log.info("解密失败");
        return "";
    }
}

