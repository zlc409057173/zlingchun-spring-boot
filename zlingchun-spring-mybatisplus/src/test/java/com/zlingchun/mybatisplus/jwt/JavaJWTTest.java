package com.zlingchun.mybatisplus.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

/**
 * @author achun
 * @create 2022/7/25
 * @description descrip
 */
@Slf4j
public class JavaJWTTest {

    /**
     * 令牌获取
     */
    @Test
    void testJavaJWT() {
        // 指定token过期时间为10秒
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.SECOND, 100);

        String token = JWT.create()
                .withHeader(new HashMap<>())  // Header
                .withClaim("userId", 21)  // Payload
                .withClaim("userName", "baobao")
                .withExpiresAt(calendar.getTime())  // 过期时间
                .sign(Algorithm.HMAC256("!34ADAS"));  // 签名用的secret
        log.info(token);
    }

    /**
     * 验证
     */
    @Test
    void checkJavaJWT() {
        // 创建解析对象，使用的算法和secret要与创建token时保持一致
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("!34ADAS")).build();
        // 解析指定的token
        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJ1c2VyTmFtZSI6ImJhb2JhbyIsImV4cCI6MTY1ODc0MTA3NiwidXNlcklkIjoyMX0.EsibKHpO0CbMhtHgbT5Fat1_meOcX-CIg3A4u2HEhLw");
        log.info("Header : {}",verify.getHeader());
        log.info("Payload : {}",verify.getPayload());
        log.info("Signature : {}",verify.getSignature());
        log.info("ExpiresAt : {}", new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(verify.getExpiresAt())); //过期时间
        //获取解析后的token中的payload信息
        log.info("userId : {}",verify.getClaim("userId").asInt());
        log.info("userName : {}",verify.getClaim("userName").asString());
    }


}
