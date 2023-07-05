package com.guangyou.rareanimal.shiro;

import com.guangyou.rareanimal.utils.JwtUtil;
import com.guangyou.rareanimal.common.lang.Result;
import com.guangyou.rareanimal.utils.JwtUtil;
import com.guangyou.rareanimal.utils.RedisUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 自定义 AuthenticatingFilter 过滤器 JwtFilter
 */
@Slf4j
@Component
public class JwtFilter extends AuthenticatingFilter {

    @Autowired
    JwtUtil jwtUtils;

    @Autowired
    private RedisUtil redisUtils;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        if(StringUtils.isEmpty(jwt)) {
            return null;
        }
        return new JwtToken(jwt);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        String jwt = request.getHeader("Authorization");
        
        if(StringUtils.isEmpty(jwt)) {
            return true;
        } else {
            // 截去 jwt字符串 的前缀"Bearer"，校验 JWT 是否失效
//            jwt = jwt.substring("Bearer ".length());
            Claims claim = jwtUtils.getClaimByToken(jwt);
            if(claim == null || jwtUtils.isTokenExpired(claim.getExpiration())) {
                throw new ExpiredCredentialsException("token已失效，请重新登录！");
            }
            // 判断 JWT 信息是否在 redis 中
            Boolean isHave = redisUtils.hasKey(jwt);
            if(!isHave){
                throw new ExpiredCredentialsException("非法token或token已失效，请重新登录！");
            }
            // 执行登录
            return executeLogin(servletRequest, servletResponse);
        }
    }

    // 执行登录出现异常的时候
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        
        Throwable throwable = e.getCause() == null ? e : e.getCause();
        Result result = Result.fail(throwable.getMessage());
        
        try {
            httpServletResponse.getWriter().print(result);
        } catch (IOException ioException) {
            log.info("\n\n ================= \n" + "  onLoginFailure,error" + ioException+  " \n================= \n\n");
        }
        return false;
    }

    /**
     * 解决跨域问题
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {

        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        httpServletResponse.setHeader("Access-Control-Allow-Origin", "*");
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", "*");
        // 跨域时会首先发送一个OPTIONS请求，这里我们给OPTIONS请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(org.springframework.http.HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }
}
