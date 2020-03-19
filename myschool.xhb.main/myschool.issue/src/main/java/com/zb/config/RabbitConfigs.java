package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfigs {
    //交换机
    public static final String myexchange="myexchange.boot";

    //上传文件消息队列
    public static final String docQueue="doc.boot.queue";
    //上传文件钥匙
    public static final String docKey="doc.key.queue";

    //上传通知消息队列
    public static final String nocQueue="noc.boot.queue";
    //上传文件钥匙
    public static final String nocKey="noc.key.queue";

    //交换机配置
    @Bean(myexchange)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
    }

    //创建上传文件消息队列
    @Bean(docQueue)
    public Queue createDocQueue(){
        Queue queue =new Queue(docQueue);
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
    public Binding bindingDoc(@Qualifier(myexchange) Exchange exchange , @Qualifier(docQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(docKey).noargs();
    }

    //将上传通知消息对列绑定到交换机上
    @Bean
    public Binding bindingNoc(@Qualifier(myexchange) Exchange exchange , @Qualifier(nocQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(nocKey).noargs();
    }

}
