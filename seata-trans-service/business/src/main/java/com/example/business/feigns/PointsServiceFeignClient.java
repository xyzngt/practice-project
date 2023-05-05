package com.example.business.feigns;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * PointsServiceClient interface
 *
 * @author zhangl
 *
 */
@Component
@FeignClient(name = "ACCOUNT",contextId = "pointsServiceFeignlient")
public interface PointsServiceFeignClient {
    /**
     * 增加积分
     * @param userId
     * @param quantity
     */
    @PostMapping("/points/increase")
    void increase(@RequestParam Long userId, @RequestParam Integer quantity);
}