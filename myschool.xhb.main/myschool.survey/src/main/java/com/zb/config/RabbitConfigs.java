package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigs {
    //交换机
    public static final String surexchange="surexchange.boot";

    //上传调查消息队列
    public static final String surQueue="sur.boot.queue";
    //上传调查钥匙
    public static final String surKey="sur.key.queue";

    //交换机配置
    @Bean(surexchange)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(surexchange).durable(true).build();
    }

    //创建上传调查消息队列
    @Bean(surQueue)
    public Queue createSurQueue(){
        Queue queue =new Queue(surQueue);
        return queue;
    }

    //将上传文件消息对列绑定到交换机上
    @Bean
    public Binding bindingDoc(@Qualifier(surexchange) Exchange exchange , @Qualifier(surQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(surKey).noargs();
    }

}
