package com.easy.boke.mq;

import com.easy.boke.config.DelayedConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.UUID;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/11
 */
@Component
@Slf4j
public class DelayProducer {

    @Autowired
    private RabbitTemplate rabbitTemplate;


    //PostConstruct会在依赖注入完成后被自动调用
    @PostConstruct
    private void init(){
//        rabbitTemplate.setChannelTransacted(true);
        rabbitTemplate.setConfirmCallback((CorrelationData correlationData, boolean ack, String cause) -> {
            String id = correlationData != null ? correlationData.getId() : "";
            if (ack) {
                log.info("消息确认成功, id:{}", id);
            } else {
                log.error("消息未成功投递, id:{}, cause:{}", id, cause);
            }
        });
    }

    public void sendCustomMsg(String msg) {
        //把其中的id缓存起来，用来生产者确认
        CorrelationData correlationData = new CorrelationData(UUID.randomUUID().toString());
        rabbitTemplate.convertAndSend(DelayedConfig.BOKE_EXCHANGE_NAME, DelayedConfig.BOKE_ROUTING_KEY, msg, correlationData);

    }
}
