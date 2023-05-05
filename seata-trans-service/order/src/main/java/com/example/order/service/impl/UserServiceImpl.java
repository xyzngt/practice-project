package com.example.order.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.entity.User;
import com.example.order.mapper.UserMapper;
import com.example.order.service.IUserService;
import com.example.order.feigns.UserServiceFeignClient;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RBloomFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * UserServiceImpl class
 *
 * @author xyzngtt
 */
@Slf4j
@Service("orderUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {
    private static final String ORDER_CACHE_KEY = "orders.users";
    @Resource
    private UserMapper userMapper;
    @Autowired
    private RedisTemplate redisTemplate;
    @Autowired
    private RBloomFilter userBloomFilter;
    @Autowired
    private UserServiceFeignClient userServiceFeignClient;

    @Override
    @Cacheable(value = ORDER_CACHE_KEY, key = "#id")
    public User getUserById(long id) {
        User user = null;
        // 使用布隆过滤器
        if (userBloomFilter.contains(id)) {
            user = userMapper.selectById(id);
        } else {
            //openFeign调用获取userid
            user = userServiceFeignClient.getUser(id);
        }
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        return user;
    }

    /**
     * 同步user信息到order user
     *
     * @param user
     */
    @Override
    public void syncUser(User user) {
        log.info("syncUser [{}]", user);
        if (user == null || user.getId() == null) {
            return;
        }
        User user1 = user.selectById(user.getId());
        if (user1 != null) {
            userMapper.update(user, new LambdaQueryWrapper<User>().eq(User::getId, user1.getId()));
        } else {
            userMapper.insert(user);
        }
        //更新缓存
        redisTemplate.opsForValue().set(String.format("%s::%s", ORDER_CACHE_KEY, user.getId()), user);
        userBloomFilter.add(user.getId());
    }

}
