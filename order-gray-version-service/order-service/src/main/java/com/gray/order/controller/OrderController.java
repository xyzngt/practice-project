package com.gray.order.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * OrederController class
 *
 * @author xyzngtt
 *
 */
@Slf4j
@RefreshScope
@RestController
public class OrderController {
    @Value("${spring.cloud.nacos.discovery.metadata.version:0}")
    private String version;
    @GetMapping("/order/version")
    public String getOrderVersion(HttpServletRequest request){
        log.info("version is {}",version);
        return version;
    }
}
