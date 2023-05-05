package com.example.order.component;

import com.alibaba.fastjson.JSON;
import com.example.order.entity.User;
import com.example.order.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * TopicReceiver class
 * 接受 mq传过来的 user 并保存到数据库
 *
 * @author xyzngtt
 *
 */
@Slf4j
@Component
public class TopicReceiver {
    @Resource
    private IUserService userService;

    @RabbitListener(queues = {"userSyncTopicQueue"})
    @RabbitHandler
    public void receiverMsg(@Payload String msg) {
        log.info("class:{},message:{}", "TopicReceiver", msg);
        if (msg == null) {
            return;
        }
        User user = JSON.parseObject(msg, User.class);
        if (Objects.isNull(user)) {
            return;
        }
        userService.syncUser(user);
    }
}
