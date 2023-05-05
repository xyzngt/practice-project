package com.example.order.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.order.dto.OrderReq;
import com.example.order.entity.Order;

/**
 * IOrderService interface
 *
 * @author xyzngtt
 * @date 2023/3/9
 */
public interface IOrderService extends IService<Order> {
    String createOrder(OrderReq orderReq);
}
