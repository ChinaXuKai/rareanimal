package com.guangyou.rareanimal.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 自定义 AuthenticationToken：JwtToken
 * @author xukai
 * @create 2022-11-05 12:45
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String jwt){
        this.token = jwt;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
