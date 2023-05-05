package com.example.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.account.entity.User;
import com.example.account.entity.UserPoints;
import com.example.account.mapper.UserMapper;
import com.example.account.mapper.UserPointsMapper;
import com.example.account.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, User> implements  UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(long userId) {
        return userMapper.selectById(userId);
    }

    @Override
    public User createUser(User user) {
        userMapper.insert(user);
        return user;
    }

    @Override
    public User updateUser(User user) {
        userMapper.update(user,new LambdaQueryWrapper<User>().eq(User::getId,user.getId()));
        return user;
    }
}
