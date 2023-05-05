package com.example.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.account.entity.User;

public interface UserService extends IService<User> {
    /**
     * 通过userId 获取User
     * @param userId userId
     * @return User
     */
    User getUserById(long userId);

    /**
     * 创建用户信息
     * @param user 请求用户信息
     * @return 返回创建的用户信息
     */
    User createUser(User user);

    /**
     * 跟新用户信息
     * @param user 请求用户信息
     * @return User
     */
    User updateUser( User user);

}
