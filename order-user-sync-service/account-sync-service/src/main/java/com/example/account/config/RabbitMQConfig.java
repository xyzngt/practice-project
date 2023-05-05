package com.example.account.config;

import com.example.account.enums.QueueEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQConfig class
 *
 * @author xyzngtt
 * @date 2023/3/9
 */
@Configuration
@EnableRabbit
@Slf4j
public class RabbitMQConfig {

    @Bean
    public AmqpTemplate amqpTemplate(RabbitTemplate rabbitTemplate){
        //使用jackson 消息转换器(发送对象时候才开启)
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setEncoding("UTF-8");
        rabbitTemplate.setMandatory(true);
        // 开启ReturnsCallback需要配置yml:publisher-returns: true
        rabbitTemplate.setReturnsCallback(returnedMessage -> {
            String correlationId = returnedMessage.getMessage().getMessageProperties().getCorrelationId();
            int replyCode = returnedMessage.getReplyCode();
            String replyText = returnedMessage.getReplyText();
            String exchange = returnedMessage.getExchange();
            String routingKey = returnedMessage.getRoutingKey();
            String queueName = returnedMessage.getMessage().getMessageProperties().getConsumerQueue();
            log.info("消息:[{}] 发送失败, 应答码:[{}],原因:[{}],交换机:[{}],队列[{}],路由键:[{}]",correlationId,replyCode,replyText,exchange,queueName,routingKey);
        });
        //开启消息确认ConfirmCallback需要配置yml:publisher-confirm-type: true
        rabbitTemplate.setConfirmCallback(((correlationData, ack, cause) ->{
            if (ack) {
                log.info("消息发送到交换机成功,correlationId:{}",correlationData.getId());
            } else {
                log.info("消息发送到交换机失败,原因:{}",cause);
            }
        } ));
        return rabbitTemplate;
    }

    @Bean("directExchange")
    public Exchange directExchange() {
        return ExchangeBuilder.directExchange(QueueEnum.QUEUE_DIRECT.getExchange()).durable(true).build();
    }

    @Bean("directQueue")
    public Queue directQueue(){
        return new Queue(QueueEnum.QUEUE_DIRECT.getName(), true, false, false);
    }

    @Bean
    public Binding directBinding(@Qualifier("directQueue")Queue queue, @Qualifier("directExchange")Exchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(QueueEnum.QUEUE_DIRECT.getRouteKey()).noargs();
    }

    @Bean("userSyncTopicExchange")
    public TopicExchange topicExchange(){
        return new TopicExchange(QueueEnum.QUEUE_USER_SYNC.getExchange());
    }
    @Bean("userSyncTopicQueue")
    public Queue topicQueue(){
        return new Queue(QueueEnum.QUEUE_USER_SYNC.getName(),true, false, false);
    }
    @Bean
    public Binding topicABinding(@Qualifier("userSyncTopicQueue")Queue queue,TopicExchange topicExchange){
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with(QueueEnum.QUEUE_USER_SYNC.getRouteKey());
    }
}
