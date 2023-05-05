package com.example.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order.entity.User;

public interface IUserService extends IService<User> {
    /**
     * 通过userid获取用户信息，若order user库不存在，则进行远程调用account的数据
     * @param id userid
     * @return user
     */
    User getUserById(long id);

    /**
     * mq同步用户信息
     * @param user
     */
    void syncUser(User user);
}
