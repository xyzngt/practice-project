package com.gray.order.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * HelloController class
 *
 * @author zyngtt
 * @date 2023/5/4 12:03
 */

@RestController
@RefreshScope
public class HelloController {
    @Value("${user.name}")
    private String name;
    @GetMapping("/hello")
    public String hello(){
        return "Hello World !!! name= " + name;
    }
}
