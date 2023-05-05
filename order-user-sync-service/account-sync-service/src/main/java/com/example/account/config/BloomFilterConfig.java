package com.example.account.config;

import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author xyzngtt
 */
@SuppressWarnings({"rawtypes", "UnstableApiUsage"})
@Slf4j
@Configuration
public class BloomFilterConfig {
    private  int expectedInsertions = 100000;
    private double fpp = 0.001;

    @Autowired
    private RedissonClient redissonClient;
    //初始化布隆过滤器，放入到spring容器里面

    @Bean("userBloomFilter")
    public RBloomFilter userBloomFilter(){
        RBloomFilter<Object> rBloomFilterUser = redissonClient.getBloomFilter("rBloomFilterUser");
        rBloomFilterUser.tryInit(expectedInsertions,fpp);
        return rBloomFilterUser;
    }


}
