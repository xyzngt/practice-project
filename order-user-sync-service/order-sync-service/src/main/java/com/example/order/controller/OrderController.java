package com.example.order.controller;

import com.example.order.dto.OrderReq;
import com.example.order.service.IOrderService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * OrderController class
 *
 * @author xyzngtt
 * @date 2023/3/9 14:32
 */
@RestController
public class OrderController {
    @Resource
    private IOrderService orderService;
    @PostMapping("/orders")
    public String createOrder(@RequestBody OrderReq orderReq){
        return orderService.createOrder(orderReq);
    }
}
