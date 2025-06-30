package com.jiangying.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolConfig {

    @Bean("categoryExecutor")
    public ThreadPoolExecutor categoryExecutor() {
        return new ThreadPoolExecutor(
                10,
                20,
                3,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>(50),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()
        );
    }
} 