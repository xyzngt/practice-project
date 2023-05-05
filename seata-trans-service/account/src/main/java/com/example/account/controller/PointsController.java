package com.example.account.controller;

import com.example.account.service.UserPointsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * PointsController class
 *
 * @author zhangl
 *
 */
@RestController
public class PointsController {
    @Resource
    private UserPointsService userPointsService;
    @PostMapping("/points/increase")
    public void increase(@RequestParam Long userId, @RequestParam Integer quantity){
        userPointsService.increase(userId, quantity);
    }

}
