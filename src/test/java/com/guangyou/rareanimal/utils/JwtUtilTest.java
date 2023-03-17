package com.guangyou.rareanimal.utils;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

/**
 * @author xukai
 * @create 2022-11-03 20:46
 */
@Slf4j
@SpringBootTest
class JwtUtilTest {

    @Autowired
    private JwtUtil jwtUtil = new JwtUtil();

    @Test
    void generateToken() {
        String token = jwtUtil.generateToken(1);
        System.out.println(token);
    }

    @Test
    void getClaimByToken() {
        Claims claimByToken = jwtUtil.getClaimByToken("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJzdWIiOiIxIiwiaWF0IjoxNjY3NDgxNjgyLCJleHAiOjE2NjgwODY0ODJ9.lOVWaUGg67RLG33xYCXMcGJRkMvvsg9nAgos8LYEovulIiWqu8ZvY8NvxfjDW5bo9PwZoHjbblR6hCJnWNRPuQ");
        System.out.println(claimByToken);
    }

    @Test
    void isExpire() {
        boolean tokenExpired = jwtUtil.isTokenExpired(new Date(new Date().getTime() + 604800000));
        System.out.println(tokenExpired);
    }
}