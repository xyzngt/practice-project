package com.gray.business.controller;

import com.gray.business.feigns.OrderServiceFeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * BusinessController class
 *
 * @author xyzngtt
 *
 */
@RestController
public class BusinessController {
    private final OrderServiceFeignClient orderServiceFeignClient;

    public BusinessController(OrderServiceFeignClient orderServiceFeignClient) {
        this.orderServiceFeignClient = orderServiceFeignClient;
    }

    /**
     * 用于验证是否请求指定的order服务
     * @return order version
     */
    @GetMapping("/business/getOrderVersion")
    public String getOrderVersion(HttpServletRequest request){
        return orderServiceFeignClient.getOrderServiceVersion(request.getHeader("version"));
    }
}
