package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigs {
    //交换机
    public static final String scoexchange="scoexchange.boot";

    //上传成绩消息队列
    public static final String scoQueue="sco.boot.queue";
    //上传成绩钥匙
    public static final String scoKey="sco.key.queue";

    //交换机配置
    @Bean(scoexchange)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(scoexchange).durable(true).build();
    }

    //创建上传成绩消息队列
    @Bean(scoQueue)
    public Queue createScoQueue(){
        Queue queue =new Queue(scoQueue);
        return queue;
    }

    //将上传成绩消息对列绑定到交换机上
    @Bean
    public Binding bindingSco(@Qualifier(scoexchange) Exchange exchange , @Qualifier(scoQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(scoKey).noargs();
    }

}
