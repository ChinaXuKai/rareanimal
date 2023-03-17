package com.guangyou.rareanimal.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * @author xukai
 * @create 2023-01-02 18:25
 */
@Configuration
@EnableAsync    //开启多线程
public class ThreadPoolConfig {

    @Bean("taskExecutor")
    public Executor asyncServiceExecutor(){
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        //设置核心线程数
        taskExecutor.setCorePoolSize(10);
        //设置最大线程数
        taskExecutor.setMaxPoolSize(25);
        //配置队列大小
        taskExecutor.setQueueCapacity(Integer.MAX_VALUE);
        //设置线程活跃时间（秒）
        taskExecutor.setKeepAliveSeconds(60);
        //设置默认线程名称
        taskExecutor.setThreadNamePrefix("野生珍稀动物保护项目");
        //等待所有任务结束后再关闭线程池
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //执行初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

}
