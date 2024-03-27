package com.citizens.mainframe.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.cache.CacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;


@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    CacheManager cacheManager() {
        return new ConcurrentMapCacheManager("accountsCache"); // Define cache name
    }

    @Bean
    ThreadPoolTaskExecutor taskExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(10); // Set to your desired core pool size
		return executor;
	}
    public class CachingConfig {
    }
    
    
    
}