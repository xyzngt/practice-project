package com.gray.business;

import com.gray.business.config.GrayVersionLoadBalancerConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClients;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author xyzngt
 */
@SpringBootApplication
@EnableFeignClients
@LoadBalancerClients(defaultConfiguration = GrayVersionLoadBalancerConfiguration.class)
public class BusinessServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(BusinessServiceApplication.class, args);
    }

}
