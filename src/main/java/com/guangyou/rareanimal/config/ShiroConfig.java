package com.guangyou.rareanimal.config;

import com.guangyou.rareanimal.shiro.AccountRealm;
import com.guangyou.rareanimal.shiro.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    JwtFilter jwtFilter;

    @Bean
    public SessionManager sessionManager(RedisSessionDAO redisSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();

        // inject redisSessionDAO
        sessionManager.setSessionDAO(redisSessionDAO);
        return sessionManager;
    }

    @Bean
    public DefaultWebSecurityManager securityManager(AccountRealm accountRealm,
                                                     SessionManager sessionManager,
                                                     RedisCacheManager redisCacheManager) {

        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager(accountRealm);

        //inject sessionManager
        securityManager.setSessionManager(sessionManager);
        
        // 执行退出时报：Default value is "id", that means your principal object has a method called "getId()，添加此行配置
        redisCacheManager.setPrincipalIdFieldName("userId");

        // inject redisCacheManager
        securityManager.setCacheManager(redisCacheManager);
        return securityManager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        
        Map<String, String> filterMap = new LinkedHashMap<>();

        filterMap.put("/**", "jwt");
        //放行 glb传输、上传、个人中心、动物档案库接口、推荐系统、文章-未登录可看、用户登录、注册、查看评论接口、
//        filterMap.put("/transmit/**", "anon");
//        filterMap.put("/upload/**", "anon");
//        filterMap.put("/personalCenter/**", "anon");
//        filterMap.put("/animal/**", "anon");
//        filterMap.put("/recommend/article", "anon");
//        filterMap.put("/recommend/articleCategory", "anon");
//        filterMap.put("/article/**", "anon");
//        filterMap.put("/user/loginUser", "anon");
//        filterMap.put("/user/registerUser", "anon");
//        filterMap.put("/comments/getCommentList/{id}", "anon");
        chainDefinition.addPathDefinitions(filterMap);
        return chainDefinition;
    }

    @Bean("shiroFilterFactoryBean")
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager,
                                                         ShiroFilterChainDefinition shiroFilterChainDefinition) {
        ShiroFilterFactoryBean shiroFilter = new ShiroFilterFactoryBean();
        shiroFilter.setSecurityManager(securityManager);
        
        Map<String, Filter> filters = new HashMap<>();
        filters.put("jwt", jwtFilter);
        shiroFilter.setFilters(filters);

        Map<String, String> filterMap = shiroFilterChainDefinition.getFilterChainMap();

        shiroFilter.setFilterChainDefinitionMap(filterMap);
        return shiroFilter;
    }
}
