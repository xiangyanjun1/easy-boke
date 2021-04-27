package com.easy.boke.mq;

import com.easy.boke.config.DelayedConfig;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/11
 */
@Component
public class DelayConsumer {
    @RabbitListener(queues = DelayedConfig.BOKE_DELAY_QUEUE)
    public void receive(Message message, Channel channel) throws IOException {
        channel.basicQos(0, 1, false);
        String s = new String(message.getBody());
        System.out.println(s);
        channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
    }
}
