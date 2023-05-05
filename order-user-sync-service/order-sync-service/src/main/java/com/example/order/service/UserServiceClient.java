package com.example.order.service;

import com.example.order.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * OpenFeign 远程获取用户信息
 * @author xyzngtt
 */
@Service("userServiceClient")
@FeignClient(value = "ACCOUNT-SYNC-SERVICE")
public interface UserServiceClient {
    /**
     * 根据id获取用户信息
     * @param id 用户id
     * @return User
     */
    @GetMapping("/users/{id}")
    User getUser(@PathVariable("id") Long id);

    /**
     * 获取所有用户信息
     * @return  List<User>
     */
    @GetMapping("/users")
    List<User> getUsers();
}
