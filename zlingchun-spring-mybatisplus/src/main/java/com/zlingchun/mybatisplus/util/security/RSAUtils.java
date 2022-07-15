package com.zlingchun.mybatisplus.util.security;

import lombok.extern.slf4j.Slf4j;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * @author achun
 * @create 2022/6/14
 * @description descrip
 */
@Slf4j
public class RSAUtils {

    /**
     * RSA最大加密明文大小 2048/8-11
     */
    private static final int MAX_ENCRYPT_BLOCK = 245;

    /**
     * RSA最大解密密文大小 2048/8
     */
    private static final int MAX_DECRYPT_BLOCK = 256;
    /**
     * 定义加密方式
     */
    public static final String KEY_RSA = "RSA";
    /**
     * 定义公钥关键词
     */
    public static final String KEY_RSA_PUBLICKEY = "RSAPublicKey";
    /**
     * 定义私钥关键词
     */
    public static final String KEY_RSA_PRIVATEKEY = "RSAPrivateKey";
    /**
     * 定义签名算法
     */
    private final static String KEY_RSA_SIGNATURE = "MD5withRSA";

    /**
     * 生成公私密钥对
     */
    public static Map<String, Object> init() {
        Map<String, Object> map = null;
        try {
            KeyPairGenerator generator = KeyPairGenerator.getInstance(KEY_RSA);
            //设置密钥对的bit数，越大越安全，但速度减慢，一般使用512或1024
            generator.initialize(2048);
            KeyPair keyPair = generator.generateKeyPair();
            // 获取公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 获取私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();
            // 将密钥对封装为Map
            map = new HashMap<String, Object>(2);
            map.put(KEY_RSA_PUBLICKEY, publicKey);
            map.put(KEY_RSA_PRIVATEKEY, privateKey);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return map;
    }

    /**
     * 获取Base64编码的公钥字符串
     */
    public static String getPublicKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PUBLICKEY);
        str = encryptBase64(key.getEncoded());
        return str;
    }

    /**
     * 获取Base64编码的私钥字符串
     */
    public static String getPrivateKey(Map<String, Object> map) {
        String str = "";
        Key key = (Key) map.get(KEY_RSA_PRIVATEKEY);
        str = encryptBase64(key.getEncoded());
        return str;
    }

    /**
     * BASE64 解码
     * @param key 需要Base64解码的字符串
     * @return 字节数组
     */
    public static byte[] decryptBase64(String key) {
        return Base64.getDecoder().decode(key);
    }

    /**
     * BASE64 编码
     * @param key 需要Base64编码的字节数组
     * @return 字符串
     */
    public static String encryptBase64(byte[] key) {
        return new String(Base64.getEncoder().encode(key));
    }

    /**
     * 公钥加密  如果大于245则分段加密
     */
    public static String encryptByPublic(String encryptingStr, String publicKeyStr){
        try {
            // 将公钥由字符串转为UTF-8格式的字节数组
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            // 获得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory;
            factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicKey);
            byte[] bytes = translate(cipher, data, Cipher.ENCRYPT_MODE);
            // 返回加密后由Base64编码的加密信息
            return encryptBase64(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥解密 如果大于256则分段解密
     */
    public static String decryptByPrivate(String encryptedStr, String privateKeyStr){
        try {
            // 对私钥解密
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 获得待解密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateKey);
            byte[] bytes = translate(cipher, data, Cipher.DECRYPT_MODE);
            // 返回UTF-8编码的解密信息
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 私钥加密  如果大于245则分段加密
     */
    public static String encryptByPrivate(String encryptingStr, String privateKeyStr){
        try {
            byte[] privateKeyBytes = decryptBase64(privateKeyStr);
            // 获得私钥
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
            // 取得待加密数据
            byte[] data = encryptingStr.getBytes(StandardCharsets.UTF_8);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PrivateKey privateKey = factory.generatePrivate(keySpec);
            // 对数据加密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, privateKey);
            byte[] bytes = translate(cipher, data, Cipher.ENCRYPT_MODE);
            // 返回加密后由Base64编码的加密信息
            return encryptBase64(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥解密 如果大于256则分段解密
     */
    public static String decryptByPublic(String encryptedStr, String publicKeyStr){
        try {
            // 对公钥解密
            byte[] publicKeyBytes = decryptBase64(publicKeyStr);
            // 取得公钥
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKeyBytes);
            // 取得待加密数据
            byte[] data = decryptBase64(encryptedStr);
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            PublicKey publicKey = factory.generatePublic(keySpec);
            // 对数据解密
            Cipher cipher = Cipher.getInstance(factory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, publicKey);
            byte[] bytes = translate(cipher, data, Cipher.DECRYPT_MODE);
            // 返回UTF-8编码的解密信息
            return new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 对数据分段加密
     */
    public static byte[] translate(Cipher cipher, byte[] data, int mode) throws Exception{
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try{
            int inputLen = data.length;
            int offSet = 0;
            byte[] cache;
            int i = 0;
            int length = MAX_DECRYPT_BLOCK;
            if(mode == Cipher.ENCRYPT_MODE){
                length = MAX_ENCRYPT_BLOCK;
            }
            // 对数据分段加密
            while (inputLen - offSet > 0) {
                if (inputLen - offSet > length) {
                    cache = cipher.doFinal(data, offSet, length);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * length;
            }
            return out.toByteArray();
        }finally {
            out.close();
        }
    }

    /**
     * 用私钥对加密数据进行签名
     */
    public static String sign(String encryptedStr, String privateKey) {
        String str = "";
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的私钥
            byte[] bytes = decryptBase64(privateKey);
            // 构造PKCS8EncodedKeySpec对象
            PKCS8EncodedKeySpec pkcs = new PKCS8EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取私钥对象
            PrivateKey key = factory.generatePrivate(pkcs);
            // 用私钥对信息生成数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initSign(key);
            signature.update(data);
            str = encryptBase64(signature.sign());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return str;
    }

    /**
     * 校验数字签名
     * @return 校验成功返回true，失败返回false
     */
    public static boolean verify(String encryptedStr, String publicKey, String sign) {
        boolean flag = false;
        try {
            //将私钥加密数据字符串转换为字节数组
            byte[] data = encryptedStr.getBytes();
            // 解密由base64编码的公钥
            byte[] bytes = decryptBase64(publicKey);
            // 构造X509EncodedKeySpec对象
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
            // 指定的加密算法
            KeyFactory factory = KeyFactory.getInstance(KEY_RSA);
            // 取公钥对象
            PublicKey key = factory.generatePublic(keySpec);
            // 用公钥验证数字签名
            Signature signature = Signature.getInstance(KEY_RSA_SIGNATURE);
            signature.initVerify(key);
            signature.update(data);
            flag = signature.verify(decryptBase64(sign));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 功能描述:格式化公私钥 C++格式
     *公钥字符串开头要加上“-----BEGIN PUBLIC KEY-----\n”，结尾加上“\n-----END PUBLIC KEY-----\n”
     * 私钥字符串开头要加上“-----BEGIN RSA PRIVATE KEY-----\n”，结尾加上“\n-----END RSA PRIVATE KEY-----\n”
     * @author xiaobu
     * @date 2020/3/18 16:40
     * @param str 要格式的字符串, flag
     * @param  flag true为公 false为私
     * @return java.lang.String
     * @version 1.0
     */
    public static String formatStr(String str,boolean flag){
        StringBuilder sb = new StringBuilder(str);
        for (int i = 0,len=sb.length(); i < len; i++) {
            if(i%64==0){
                sb.insert(i, "\n");
            }
        }
        if(flag){
            sb = new StringBuilder("-----BEGIN PUBLIC KEY-----").append(sb).append("\n-----END PUBLIC KEY-----\n");
        }else {
            sb = new StringBuilder("-----BEGIN RSA PRIVATE KEY-----").append(sb).append("\n-----END RSA PRIVATE KEY-----\n");
        }
        return sb.toString();
    }



    /**
     * 测试方法
     */
    public static void main(String[] args) {
        Map<String, Object> map = new HashMap<String, Object>(2);
        map = RSAUtils.init();
        String  publicKey= RSAUtils.getPublicKey(map);
        String  privateKey= RSAUtils.getPrivateKey(map);
        log.info("publicKey = " + publicKey);
        log.info("privateKey = " + privateKey);
        log.info("formatStr(publicKey,false) = " + formatStr(publicKey, true));
        log.info("formatStr(privateKey,false) = " + formatStr(privateKey, false));
        String str = "1 InnoDB 支持表锁和行锁，使用索引作为检索条件修改数据时采用行锁，否则采用表锁。\n" +
                "2 InnoDB 自动给修改操作加锁，给查询操作不自动加锁" +
                "3 行锁可能因为未使用索引而升级为表锁，所以除了检查索引是否创建的同时，也需要通过explain执行计划查询索引是否被实际使用。, RSA!";
        // 公钥加密，私钥解密
        String enStr1 = RSAUtils.encryptByPublic(str, publicKey);
        log.info("公钥加密后："+enStr1);
        String deStr1 = RSAUtils.decryptByPrivate(enStr1, privateKey);
        log.info("私钥解密后："+deStr1);
        // 私钥加密，公钥解密
        String enStr2 = RSAUtils.encryptByPrivate(str, privateKey);
        log.info("私钥加密后："+enStr2);
        String deStr2 = RSAUtils.decryptByPublic(enStr2, publicKey);
        log.info("公钥解密后："+deStr2);
        // 产生签名
        String sign = sign(enStr2, privateKey);
        log.info("签名:"+sign);
        // 验证签名
        boolean status = verify(enStr2, publicKey, sign);
        log.info("状态:"+status);

    }

}