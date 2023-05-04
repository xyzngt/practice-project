package com.gray.business.balancer;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.*;
import org.springframework.cloud.loadbalancer.core.NoopServiceInstanceListSupplier;
import org.springframework.cloud.loadbalancer.core.ReactorServiceInstanceLoadBalancer;
import org.springframework.cloud.loadbalancer.core.ServiceInstanceListSupplier;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * GrayVersionLoadBalancer class
 *自定义灰度负载
 * @author xyzngtt
 *
 */
@Slf4j
public class GrayVersionLoadBalancer implements ReactorServiceInstanceLoadBalancer {

     private ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider;

    public GrayVersionLoadBalancer(ObjectProvider<ServiceInstanceListSupplier> serviceInstanceListSupplierProvider) {
        this.serviceInstanceListSupplierProvider = serviceInstanceListSupplierProvider;
    }

    @Override
    public Mono<Response<ServiceInstance>> choose(Request request) {
        ServiceInstanceListSupplier supplier = this.serviceInstanceListSupplierProvider.getIfAvailable(NoopServiceInstanceListSupplier::new);
        RequestDataContext responseData = (RequestDataContext) request.getContext();
        String version =  responseData.getClientRequest().getHeaders().getFirst("version");
        log.info("请求 version[{}]", version);
        return supplier.get(request).next().map(list->getInstanceResponse(list,version));
    }
    private  Response<ServiceInstance> getInstanceResponse(List<ServiceInstance> instances,String version) {
        //无可用实例
        if (instances.isEmpty()) {
            return new EmptyResponse();
        }
        //未使用version
        if (StringUtils.isEmpty(version)) {
            return this.getRandomInstanceResponse(instances);
        }
        // 匹配实例
        List<ServiceInstance> serviceInstances = instances.stream()
                .filter(instance -> version.equalsIgnoreCase(instance.getMetadata().get("version")))
                .collect(Collectors.toList());

        if (!serviceInstances.isEmpty()) {
             instances = serviceInstances;
        }
        return this.getRandomInstanceResponse(instances);
    }

    /**
     * 随机
     * @param instances instances
     * @return ServiceInstance
     */
    private Response<ServiceInstance> getRandomInstanceResponse(List<ServiceInstance> instances){
        int index = ThreadLocalRandom.current().nextInt(instances.size());
        ServiceInstance instance = instances.get(index);
        return new DefaultResponse(instance);
    }
}
