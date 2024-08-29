package com.sixplus.server.api.core.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class ThreadPoolConfig {

    @Bean(destroyMethod = "shutdown")
    public ThreadPoolTaskExecutor threadPoolTaskExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 최대 스레드 개수
        taskExecutor.setMaxPoolSize(10);
        // 로그에 기록될 스레드의 명은 다음과 같이 시작해야한다.
        taskExecutor.setThreadNamePrefix("SP AsyncExecutor-");
        taskExecutor.afterPropertiesSet();
        return taskExecutor;
    }
}
