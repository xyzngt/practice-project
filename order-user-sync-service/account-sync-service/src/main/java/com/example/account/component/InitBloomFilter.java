package com.example.account.component;

import com.example.account.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * 初始化布隆过滤器
 * @author xyzngtt
 *
 */
@Slf4j
@Component
public class InitBloomFilter implements InitializingBean {
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RBloomFilter<Long> userBloomFilter;
    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("初始化布隆过滤器...");
        List<Long> userIds = userMapper.selectAllUserId();
        userIds.stream().forEach(id->{
            userBloomFilter.add(id);
        });
        log.info("初始化用户布隆过滤器完成，数量[{}]",userIds.size());
    }
}
