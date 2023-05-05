package com.example.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.account.component.TopicSender;
import com.example.account.entity.User;
import com.example.account.mapper.UserMapper;
import com.example.account.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author xyzngtt
 */
@Slf4j
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    private static final String ACCOUNT_CACHE_KEY = "account.users";
    @Resource
    private UserMapper userMapper;
    @Resource
    private RedisTemplate<String ,User> redisTemplate;
    @Autowired
    private TopicSender topicSender;
    @Autowired
    private RBloomFilter<Long> userBloomFilter;


    @Override
    @Cacheable(value = ACCOUNT_CACHE_KEY,key = "#id")
    public User getUserById(long id) {
        log.info("查询用户id[{}]",id);
        //使用布隆过滤器
        if (!userBloomFilter.contains(id)){
            throw new RuntimeException("User not found");
        }
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    @Override
    public User createUser(User userReq) {
        if (userReq == null) {
            throw new RuntimeException("userReq is null");
        }
        User user = new User();
        BeanUtils.copyProperties(userReq, user);
        userMapper.insert(user);
        //刷新缓存并发送同步消息到order
        redisTemplate.opsForValue().set(String.format("%s::%s",ACCOUNT_CACHE_KEY,user.getId()),user);
        //添加布隆过滤器
        userBloomFilter.add(user.getId());
        topicSender.send(user);
        return user;
    }


    @Override
    public User updateUser(User userReq) {
        User user = new User();
        BeanUtils.copyProperties(userReq,user);
        int count = userMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getId, userReq.getId()));
        User userResult = null;
        // 更新成功则刷新缓存
        if (count == 1){
            userResult = userMapper.selectById(userReq.getId());
            //刷新缓存并同步到order-service
            redisTemplate.opsForValue().set(String.format("%s::%s",ACCOUNT_CACHE_KEY,user.getId()),userResult);
            topicSender.send(user);
        }
        return userResult;
    }

}
