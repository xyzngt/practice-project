package com.example.account.component;

import com.example.account.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * TopicSender class
 *
 * @author xyzngtt
 * @date 2023/3/9
 */
@Slf4j
@Component
public class TopicSender {
    private final RabbitTemplate rabbitTemplate;

    public TopicSender(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(Object object) {
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        log.info("class:{},message:{}","TopicSender",object.toString());
        this.rabbitTemplate.convertAndSend(QueueEnum.QUEUE_USER_SYNC.getExchange(),QueueEnum.QUEUE_USER_SYNC.getRouteKey(),object,correlationData);
    }

}
