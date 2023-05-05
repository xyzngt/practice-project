package com.example.business.service.impl;


import com.example.business.dto.OrderReq;
import com.example.business.feigns.OrderServiceFeignClient;
import com.example.business.feigns.PointsServiceFeignClient;
import com.example.business.service.BusinessService;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * BussinessServiceImpl class
 *
 * @author zhangl
 * @date 2023/4/6 13:07
 */
@Service("businessService")
public class BusinessServiceImpl implements BusinessService {
    @Autowired
    private OrderServiceFeignClient orderServiceFeignClient;
    @Autowired
    private PointsServiceFeignClient pointsServiceFeignClient;

    @Override
    @GlobalTransactional
    @Transactional(rollbackFor = Exception.class)
    public void create(OrderReq orderReq ) {
        // 创建订单
        orderServiceFeignClient.createOrder(orderReq);

        // 增加积分
        int quantity = orderReq.getQuantity() * 10;
        pointsServiceFeignClient.increase(orderReq.getUserId(),quantity);
    }

}
