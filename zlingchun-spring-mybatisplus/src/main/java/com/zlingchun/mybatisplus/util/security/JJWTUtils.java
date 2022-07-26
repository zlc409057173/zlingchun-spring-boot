package com.zlingchun.mybatisplus.util.security;

import cn.hutool.crypto.asymmetric.RSA;
import com.zlingchun.mybatisplus.constant.JWTConstant;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Date;
import java.util.Map;

/**
 * @author achun
 * @create 2022/7/25
 * @description descrip
 */
@Slf4j
public class JJWTUtils {

    /**
     * 根据用户id和昵称生成token
     * @return JWT规则生成的token
     */
    public static String getJwtToken(Map<String, Object> payload){
        JwtBuilder builder = Jwts.builder()
                .setSubject("baobao-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWTConstant.EXPIRE));
        payload.forEach(builder::claim);
                // 传入Key对象
        builder.signWith(Keys.hmacShaKeyFor(JWTConstant.APP_SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256);
        return builder.compact();
    }

    /**
     * 根据用户id和昵称生成token
     * @return JWT规则生成的token
     */
    public static String getJwtToken(Map<String, Object> payload, Long expire){
        JwtBuilder builder = Jwts.builder()
                .setSubject("baobao-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire));
        payload.forEach(builder::claim);
        // 传入Key对象
        builder.signWith(Keys.hmacShaKeyFor(JWTConstant.APP_SECRET.getBytes(StandardCharsets.UTF_8)), SignatureAlgorithm.HS256);
        return builder.compact();
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static Integer checkToken(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return JWTConstant.TOKEN_EMPTY;
        }
        try {
            Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(JWTConstant.APP_SECRET.getBytes(StandardCharsets.UTF_8))).build().parseClaimsJws(jwtToken);
            return JWTConstant.TOKEN_ACTIVE;
        } catch (ExpiredJwtException e) {
            log.error("token 时间过期, {}", e.getMessage());
            return JWTConstant.TOKEN_EXPIRED;
        } catch (UnsupportedJwtException e) {
            log.error("token 不支持, {}", e.getMessage());
            return JWTConstant.TOKEN_UNSUPPORTED;
        } catch (MalformedJwtException e) {
            log.error("token 格式错误, {}", e.getMessage());
            return JWTConstant.TOKEN_MALFORMED;
        } catch (SignatureException e) {
            log.error("token 签名错误, {}", e.getMessage());
            return JWTConstant.TOKEN_SIGNATURE;
        } catch (IllegalArgumentException e) {
            log.error("token 参数错误, {}", e.getMessage());
            return JWTConstant.TOKEN_ILLEGALARGUMENT;
        }
    }

    /**
     * 判断token是否存在与有效
     * @param request Http请求对象
     * @return 如果token有效返回true，否则返回false
     */
    public static Integer checkToken(HttpServletRequest request) {
        // 从http请求头中获取token字符串
        String jwtToken = request.getHeader("token");
        return checkToken(jwtToken);
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static Jws<Claims> decode(String jwtToken) {
        // 传入Key对象
        return Jwts.parserBuilder().setSigningKey(Keys.hmacShaKeyFor(JWTConstant.APP_SECRET.getBytes(StandardCharsets.UTF_8))).build().parseClaimsJws(jwtToken);
    }

    /**
     * =========================== 非对称 =================================
     */

    /**
     * 根据用户id和昵称生成token
     * @return JWT规则生成的token
     */
    public static String getJwtTokenRsa(Map<String, Object> payload){
        // 利用hutool创建RSA
        RSA rsa = new RSA(JWTConstant.RSA_PRIVATE_KEY, null);
        RSAPrivateKey privateKey = (RSAPrivateKey) rsa.getPrivateKey();
        JwtBuilder builder = Jwts.builder()
                .setSubject("baobao-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + JWTConstant.EXPIRE));
        payload.forEach(builder::claim);
                // 签名指定私钥
        builder.signWith(privateKey, SignatureAlgorithm.RS256);
        return builder.compact();
    }

    /**
     * 根据用户id和昵称生成token
     * @return JWT规则生成的token
     */
    public static String getJwtTokenRsa(Map<String, Object> payload, Long expire){
        // 利用hutool创建RSA
        RSA rsa = new RSA(JWTConstant.RSA_PRIVATE_KEY, null);
        RSAPrivateKey privateKey = (RSAPrivateKey) rsa.getPrivateKey();
        JwtBuilder builder = Jwts.builder()
                .setSubject("baobao-user")
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expire));
        payload.forEach(builder::claim);
        // 签名指定私钥
        builder.signWith(privateKey, SignatureAlgorithm.RS256);
        return builder.compact();
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static Jws<Claims> decodeRsa(String jwtToken) {
        RSA rsa = new RSA(null, JWTConstant.RSA_PUBLIC_KEY);
        RSAPublicKey publicKey = (RSAPublicKey) rsa.getPublicKey();
        // 验签指定公钥
        return Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwtToken);
    }

    /**
     * 判断token是否存在与有效
     * @param jwtToken token字符串
     * @return 如果token有效返回true，否则返回false
     */
    public static Integer checkTokenRsa(String jwtToken) {
        if(StringUtils.isEmpty(jwtToken)) {
            return JWTConstant.TOKEN_EMPTY;
        }
        // 验签指定公钥
        RSA rsa = new RSA(null, JWTConstant.RSA_PUBLIC_KEY);
        RSAPublicKey publicKey = (RSAPublicKey) rsa.getPublicKey();
        try {
            Jwts.parserBuilder().setSigningKey(publicKey).build().parseClaimsJws(jwtToken);
            return JWTConstant.TOKEN_ACTIVE;
        } catch (ExpiredJwtException e) {
            log.error("token 时间过期, {}", e.getMessage());
            return JWTConstant.TOKEN_EXPIRED;
        } catch (UnsupportedJwtException e) {
            log.error("token 不支持, {}", e.getMessage());
            return JWTConstant.TOKEN_UNSUPPORTED;
        } catch (MalformedJwtException e) {
            log.error("token 格式错误, {}", e.getMessage());
            return JWTConstant.TOKEN_MALFORMED;
        } catch (SignatureException e) {
            log.error("token 签名错误, {}", e.getMessage());
            return JWTConstant.TOKEN_SIGNATURE;
        } catch (IllegalArgumentException e) {
            log.error("token 参数错误, {}", e.getMessage());
            return JWTConstant.TOKEN_ILLEGALARGUMENT;
        }
    }
}
