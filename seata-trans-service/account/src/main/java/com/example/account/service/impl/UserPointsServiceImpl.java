package com.example.account.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.account.entity.UserPoints;
import com.example.account.mapper.UserPointsMapper;
import com.example.account.service.UserPointsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * UserPointsServiceImpl class
 *
 * @author zhangl
 *
 */
@Service("userPointsService")
public class UserPointsServiceImpl extends ServiceImpl<UserPointsMapper, UserPoints> implements UserPointsService {
    @Resource
    private UserPointsMapper userPointsMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void increase(Long userId, Integer quantity) {
        UserPoints userPoints = userPointsMapper.selectOne(new LambdaQueryWrapper<UserPoints>()
                .eq(UserPoints::getUserId, userId));
        if (userPoints == null) {
            userPointsMapper.insert(new UserPoints().setUserId(userId).setQuantity(quantity));
        }else {
            userPoints.setQuantity(userPoints.getQuantity() + quantity);
            userPointsMapper.update(userPoints,new LambdaQueryWrapper<UserPoints>()
                    .eq(UserPoints::getUserId,userId));
        }
    }
}
