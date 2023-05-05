package com.example.business.feigns;

import com.example.business.dto.OrderReq;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * OrderServiceClient class
 *
 * @author zhangl
 *
 */
@Component
@FeignClient(name = "ORDER",contextId = "orderClient")
public interface OrderServiceFeignClient {
    /**
     * 创建订单
     * @param orderReq 订单信息
     * @return
     */
    @PostMapping("/orders")
    String createOrder(@RequestBody OrderReq orderReq);

}
