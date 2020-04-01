package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigs {
    //交换机
    public static final String myexchange="myexchange.boot";

    //上传成绩消息队列
    public static final String scoQueue="sco.boot.queue";
    //上传成绩钥匙
    public static final String scoKey="sco.key.queue";

    //上传调查消息队列
    public static final String surQueue="sur.boot.queue";
    //上传调查钥匙
    public static final String surKey="sur.key.queue";

    //上传通知消息队列
    public static final String nocQueue="noc.boot.queue";
    //上传文件钥匙
    public static final String nocKey="noc.key.queue";

    //交换机配置
    @Bean(myexchange)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
    }

    //创建上传调查消息队列
    @Bean(surQueue)
    public Queue createSurQueue(){
        Queue queue =new Queue(surQueue);
        return queue;
    }

    //创建上传成绩消息队列
    @Bean(scoQueue)
    public Queue createScoQueue(){
        Queue queue =new Queue(scoQueue);
        return queue;
    }
    //创建上传通知消息队列
    @Bean(nocQueue)
    public Queue createNocQueue(){
        Queue queue =new Queue(nocQueue);
        return queue;
    }

    //将上传文件消息对列绑定到交换机上
    @Bean
    public Binding bindingDoc(@Qualifier(myexchange) Exchange exchange , @Qualifier(surQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(surKey).noargs();
    }

    //将上传通知消息对列绑定到交换机上
    @Bean
    public Binding bindingNoc(@Qualifier(myexchange) Exchange exchange , @Qualifier(nocQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(nocKey).noargs();
    }

    //将上传成绩消息对列绑定到交换机上
    @Bean
    public Binding bindingSco(@Qualifier(myexchange) Exchange exchange , @Qualifier(scoQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(scoKey).noargs();
    }

}
