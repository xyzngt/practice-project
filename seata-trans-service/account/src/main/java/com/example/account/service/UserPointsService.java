package com.example.account.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.account.entity.UserPoints;

/**
 * UserPointsService interface
 *
 * @author zhangl
 * @date 2023/4/6 13:35
 */
public interface UserPointsService extends IService<UserPoints> {
    /**
     * 增加积分
     *
     * @param userId  userId
     * @param quantity 积分
     */
    void increase(Long userId, Integer quantity);

}
