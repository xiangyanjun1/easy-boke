package com.easy.boke.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @Description
 * @Author xiangyanjun
 * @Version V1.0.0
 * @Since 1.0
 * @Date 2021/3/11
 */
@Configuration
public class DelayedConfig {
    public static final String BOKE_DELAY_EXCHANGE_NAME = "boke.delay.exchange";
    public static final String BOKE_DELAY_QUEUE = "boke.delay.queue";
    public static final String BOKE_DELAY_ROUTING_KEY = "bike.delay.routing.key";

    public static final String BOKE_EXCHANGE_NAME = "boke.exchange";
    public static final String BOKE_QUEUE = "boke.queue";
    public static final String BOKE_ROUTING_KEY = "bike.routing.key";

    @Bean("bokeQueue")
    public Queue bokeQueue(){
        Map<String, Object> args = new HashMap<>(2);
        // x-dead-letter-exchange    这里声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", BOKE_DELAY_EXCHANGE_NAME);
        // x-dead-letter-routing-key  这里声明当前队列的死信路由key
        args.put("x-dead-letter-routing-key", BOKE_DELAY_ROUTING_KEY);
        // x-message-ttl  声明队列的TTL
        args.put("x-message-ttl", 1000);
        return QueueBuilder.durable(BOKE_QUEUE).withArguments(args).build();
    }

    @Bean("bokeExchange")
    public DirectExchange bokeExchange(){
        return new DirectExchange(BOKE_EXCHANGE_NAME);
    }

    @Bean
    public Binding bokeBinding(@Qualifier("bokeQueue") Queue bokeQueue,
                               @Qualifier("bokeExchange") DirectExchange bokeExchange){
        return BindingBuilder.bind(bokeQueue).to(bokeExchange).with(BOKE_ROUTING_KEY);
    }


    @Bean("bokeDelayQueue")
    public Queue bokeDelayQueue(){
        return new Queue(BOKE_DELAY_QUEUE);
    }

    @Bean("bokeDelayExchange")
    public DirectExchange bokeDelayExchange(){
        return new DirectExchange(BOKE_DELAY_EXCHANGE_NAME);
    }

    @Bean
    public Binding bokeDelayBinding(@Qualifier("bokeDelayQueue") Queue bokeDelayQueue,
                                    @Qualifier("bokeDelayExchange") DirectExchange bokeDelayExchange){
        return BindingBuilder.bind(bokeDelayQueue).to(bokeDelayExchange).with(BOKE_DELAY_ROUTING_KEY);
    }

}
