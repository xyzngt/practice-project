package com.example.account.enums;

import lombok.Getter;

/**
 * 消息队列枚举类
 *
 */
@Getter
public enum QueueEnum {
    /**
     * 数据同步队列
     */
    QUEUE_USER_SYNC("user.sync.topic", "userSyncTopicQueue", "user.topic.msg"),
    QUEUE_DIRECT("directExchange", "directQueue", "directRoutingKey")
    ;

    /**
     * 交换名称
     */
    private final String exchange;
    /**
     * 队列名称
     */
    private final String name;
    /**
     * 路由键
     */
    private final String routeKey;

    QueueEnum(String exchange, String name, String routeKey) {
        this.exchange = exchange;
        this.name = name;
        this.routeKey = routeKey;
    }
}
