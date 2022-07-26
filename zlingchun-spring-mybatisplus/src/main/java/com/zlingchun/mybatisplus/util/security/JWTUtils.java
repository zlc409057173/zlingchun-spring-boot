package com.zlingchun.mybatisplus.util.security;

import cn.hutool.crypto.asymmetric.RSA;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Calendar;
import java.util.Map;

public class JWTUtils {
    // 签名密钥
    private static final String SECRET = "!DAR$";

    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String getToken(Map<String,String> payload){
        // 指定token过期时间为7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach((k,v) -> builder.withClaim(k,v));
        // 指定过期时间和签名算法
        builder.withExpiresAt(calendar.getTime());
        return builder.sign(Algorithm.HMAC256(SECRET));
    }


    /**
     * 解析token中payload
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT parseToken(String token){
        return JWT.require(Algorithm.HMAC256(SECRET)).build().verify(token);
    }

    /**
     * 验证token
     * @param token token字符串
     * @return 解析后的token
     */
    public static void verify(String token){
        JWTUtils.parseToken(token);
    }

    /**
     * =========================== 非对称 =================================
     */
    private static final String RSA_PRIVATE_KEY = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCdd+o47y4CLVdlkeXRDDsxjo+4JvlTU+k8t/jxeMp/sQUkJ1pPVr7djB09MwS/EAMq7Q1iSahZqFQqtx7lTTQBYqHepfDPMzlvzKv48NSAsa1JZyt6ncMdvv8Kw9ioyqmCkz/yU7ruDNcZ0a2oMXoDsniokfdCkddCXAJNlgfpWMjavwLkFY/r3txfrwM4RdewUWLBAHFGwR+M+4KmUwdIIr1yJ7VW+Bq5VJeORxctR+HYhTM/+aWH2LAeH3Q+MofpZVr947Jri9ACSrJxJMFnWhej5fcboiJTuAay6wzjrdTFvpaHAVjPPGIyMNqFAmHrRKQ+tcC1VTYFUrhPvrRHAgMBAAECggEBAJH9kxGl7inmj5iDY2YkyNxxJ527xHwU17CLgCmfq14naHTUZ04XRm1wcctDeasJ1WX+0OHHGGXHQgE4NVSx9v0xzJD3mAMBosacIvRxkxKdp+Ld29maWkNByILagAw9mFqO2kJ4jl4VWXd+hlnhRRYYaPUgHvWA8Ol9FEWKS1tyl6JPfeGoNtyjH7qyYa1YUA/99C4aNKbnkdf+WtI/IHGjwnrkic9UDONtF1uKEmHvtczfcU972/N2poN0/sijxb4+b8rIz9m+JIh1ya9WYX8x1zMdcSYLnByZ2MNzCy+f+bvHMeVR5Ge2zT1tctWjlKZwfWvrECEVNPDmm2fw9QECgYEA2MLnh+qcwfxniIHV7LLnMiv24KKyHjGaI4oiKUVX7T6quU5Iv6Y/X8GOH4DrAJgvLnoxoxCXg3b8kuH7Eyk/MNBMMtutwzMboFgdgx/3z98s1j1+p0rPY/HmWDXEn5FsZwR5iHjqk6LB5F89YBcKFowdkKfwSYJ6vW2gVfW8RJcCgYEAuflGEflpmcVtB1y+X8QfY/2R8A0wnhu9rrlm53Le6W3cx+mYw2JUXmDuTFUUtPOq9Cqh5457IPTWvx4pj4FoLGIwl0rKZmU+EnJ72LQrwp90LjNAXOmtsPdQxAnkVaXvalW1cRldWBfWL4o8Losh8iRyuyiVSDpqNBNRH4SYk9ECgYEA1OXyJIPhvYJcai4j4F0DSTr3O0FxMsjDPR7iWKjcKJABfQoP1/TAkBt6rSShLK9MwBiSgje8qQoIH3hHc1Vy1E3yWY18yJl9C+sf6XdPneL0leRGbqfCtMaV2JPO8G8UCsbRW6Jvu9wDFnwYuzVF2kl6jGb2Ui0OwtlWvrciWDcCgYEApHeEtkSiVTE5uLcWTU7+i8kmFOs1Qb46w5iHHsyBwmI8TDeTt8bv4f9K/wqHI3gSiZhiUou47G5as+4SxyykWgwD+OhVkrSabmn8iKrdwGKPtoPwubqWttgg/x3YZr42uw1NRtYtrMh3zNctXeQBg0kkhcvgKV4vqhojNgJnhOECgYAaQ217XIQF+WU0+aub0Wi3dtIlyzv5yqC9PeXm+nFVernW/fcaOYXHKKOYBBPNKmpDGacND5pnYbZaWGcl5UIc1P2E4+Yat4v52Sj0YC9kvivnNb5ERwnENXhH21AfKdsOXZexXWG8yxTwTV8zcuHAivhQ0rxthellFnW/8L8upQ==";
    private static final String RSA_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnXfqOO8uAi1XZZHl0Qw7MY6PuCb5U1PpPLf48XjKf7EFJCdaT1a+3YwdPTMEvxADKu0NYkmoWahUKrce5U00AWKh3qXwzzM5b8yr+PDUgLGtSWcrep3DHb7/CsPYqMqpgpM/8lO67gzXGdGtqDF6A7J4qJH3QpHXQlwCTZYH6VjI2r8C5BWP697cX68DOEXXsFFiwQBxRsEfjPuCplMHSCK9cie1VvgauVSXjkcXLUfh2IUzP/mlh9iwHh90PjKH6WVa/eOya4vQAkqycSTBZ1oXo+X3G6IiU7gGsusM463Uxb6WhwFYzzxiMjDahQJh60SkPrXAtVU2BVK4T760RwIDAQAB";

    /**
     * 生成token
     * @param payload token携带的信息
     * @return token字符串
     */
    public static String getTokenRsa(Map<String,String> payload){
        // 指定token过期时间为7天
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, 7);
        JWTCreator.Builder builder = JWT.create();
        // 构建payload
        payload.forEach(builder::withClaim);
        // 利用hutool创建RSA
        RSA rsa = new RSA(RSA_PRIVATE_KEY, null);
        // 获取私钥
        RSAPrivateKey privateKey = (RSAPrivateKey) rsa.getPrivateKey();
        // 签名时传入私钥
        return builder.withExpiresAt(calendar.getTime()).sign(Algorithm.RSA256(null, privateKey));
    }

    /**
     * 解析token
     * @param token token字符串
     * @return 解析后的token
     */
    public static DecodedJWT decodeRsa(String token){
        // 利用hutool创建RSA
        RSA rsa = new RSA(null, RSA_PUBLIC_KEY);
        // 获取RSA公钥
        RSAPublicKey publicKey = (RSAPublicKey) rsa.getPublicKey();
        // 验签时传入公钥
        JWTVerifier jwtVerifier = JWT.require(Algorithm.RSA256(publicKey, null)).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT;
    }
}