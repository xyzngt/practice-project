package com.gray.business.config;

import com.gray.business.balancer.GrayVersionLoadBalancer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.client.ConditionalOnDiscoveryEnabled;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.loadbalancer.core.ReactorLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * GrayVersionLoadBalancerConfiguration class
 *
 * @author zhangl
 * @date 2023/5/4 17:13
 */
@ConditionalOnDiscoveryEnabled
@Configuration
@Slf4j
public class GrayVersionLoadBalancerConfiguration {
    @Bean
    public ReactorLoadBalancer<ServiceInstance> reactorServiceInstanceLoadBalancer(Environment environment, LoadBalancerClientFactory loadBalancerClientFactory) {
        String name = environment.getProperty(LoadBalancerClientFactory.PROPERTY_NAME);
        log.info("初始化灰度负载[{}]", name);
        return new GrayVersionLoadBalancer(loadBalancerClientFactory.getLazyProvider(name, ServiceInstanceListSupplier.class));
    }
}
