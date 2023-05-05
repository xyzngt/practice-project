package com.example.business.controller;

import com.example.business.dto.OrderReq;
import com.example.business.service.BusinessService;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

/**
 * BusinessController class
 *
 * @author zhangl
 * @date 2023/4/6 13:03
 */
@RestController
@RequestMapping("/business")
public class BusinessController {
    private final BusinessService businessService;

    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    @PostMapping("/create")
    public void create(@RequestBody OrderReq req){
        businessService.create(req);
    }
    /**
     * 成功场景
     */
    @GetMapping("/success")
    public void success(){
        OrderReq orderReq = new OrderReq();
        orderReq.setUserId(1234567890L);
        orderReq.setPrice(new BigDecimal("100"));
        orderReq.setProductId(1234567890L);
        orderReq.setQuantity(10);
        businessService.create(orderReq);
    }

    /**
     * 模拟失败回滚场景
     */
    @GetMapping("/rollback")
    public void rollback(){
        OrderReq orderReq = new OrderReq();
        orderReq.setUserId(1234567890L);
        orderReq.setPrice(new BigDecimal("100"));
        orderReq.setProductId(1234567890L);
        orderReq.setQuantity(9999999);
        businessService.create(orderReq);
    }
}
