package com.guangyou.rareanimal.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * jwt工具类
 */
@Slf4j
@Data
@Component
@ConfigurationProperties(prefix = "rareanimal.jwt")
public class JwtUtil {

    private String secret;
    private long expire;
    private String header;

    /**
     * 生成jwt token
     */
    public String generateToken(long userId) {
        Date nowDate = new Date();
        // 设置过期时间
        Date expireDate = new Date(nowDate.getTime() +  expire* 1000);
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")        //设置头信息
                .setSubject(userId+"")                     //设置主题
                .setIssuedAt(nowDate)                      //设置签发 声明
                .setExpiration(expireDate)                 //设置过期时间为7天
                .signWith(SignatureAlgorithm.HS512, secret)//设置签名的加密算法和盐
                .compact();
    }


    /**
     * 获取 jwt 主体信息
     * @param token jwt-token
     * @return 主体信息 Claims
     */
    public Claims getClaimByToken(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(token)
                    .getBody();
        }catch (Exception e){                   //令牌错误
            log.debug("validate is token error ", e);
            return null;
        }
    }

    /**
     * token是否过期
     * @return  true：过期
     * @param expiration
     */
    public boolean isTokenExpired(Date expiration) {
        return expiration.before(new Date());
    }
}
