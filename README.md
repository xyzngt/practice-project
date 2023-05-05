# 分布式中级训练营课后作业
项目结构：
```
practice-project
├── order-user-sync-service  -- 作业1
├   ├── account-sync-service -- 用户服务
├   └── order-sync-service   -- 订单服务
├ 
├── seata-trans-service -- 作业2
├   ├── account  -- 用户服务
├   ├── business -- 业务服务
├   └── order    -- 订单服务
├ 
└── order-gray-version-service -- 作业3
    ├── business-service -- 业务服务
    └── order-service    -- 订单服务

```

## 作业1 order-user-sync-service
使用Redis实现分布式缓存以及MQ实现信息同步和业务解耦，使用OpenFeign实现远程调用

## 作业2 seata-trans-service
使用Nacos作为注册中心和配置中心，使用Seata实现分布式事务，实现全局事务的回滚,使用OpenFeign实现远程调用。
`business服务`聚合业务功能，调用`order服务`生成订单，调用`account服务`增加积分，
在业务方法`BusinessService#create`上使用`@GlobalTransactional`接入SEATA的AT全局事务

## 作业3 order-gray-version-service
使用Nacos作为注册中心和配置中心，实现接口`ReactorServiceInstanceLoadBalancer`完成自定义的灰度负载均衡策略`GrayVersionLoadBalancer`，
从而实现灰度发布功能
