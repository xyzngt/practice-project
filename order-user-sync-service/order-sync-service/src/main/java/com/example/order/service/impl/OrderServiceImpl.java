package com.example.order.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.order.dto.OrderReq;
import com.example.order.entity.Order;
import com.example.order.entity.User;
import com.example.order.mapper.OrderMapper;
import com.example.order.service.IOrderService;
import com.example.order.service.IUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.UUID;

/**
 * OrderServiceImpl class
 *
 * @author xyzngtt
 *
 */
@Service("orderService")
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order>  implements IOrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private IUserService userService;


    @Transactional(rollbackFor = Exception.class)
    @Override
    public String createOrder( OrderReq orderReq) {

        // 校验用户信息
        User user = userService.getUserById(orderReq.getUserId());
        Order order = new Order();
        order.setUserId(user.getId());
        order.setOrderSn(UUID.randomUUID().toString());
        order.setProductId(orderReq.getProductId());
        order.setProductPrice(orderReq.getPrice());
        order.setProductQuantity(orderReq.getQuantity());
        orderMapper.insert(order);
        return order.getOrderSn();
    }
}
