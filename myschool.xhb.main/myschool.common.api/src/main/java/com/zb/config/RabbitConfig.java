package com.zb.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
    public static final String CLASSQUEUE="class.queue";
    public static final String jobQueue="job.leave.queue";
    public static final String myexchange="myexchange.boot";
    public static final String classinfo="classinfo.queue";

    @Bean(myexchange)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
    }

    @Bean(classinfo)
    public Queue createQgQueue(){
        Queue queue=new Queue(classinfo);
        return queue;
    }

    @Bean(CLASSQUEUE)
    public Queue createEmailQueue(){
        Queue queue=new Queue(CLASSQUEUE);
        return queue;
    }

    @Bean(jobQueue)
    public Queue createSmsQueue(){
        Queue queue=new Queue(jobQueue);
        return queue;
    }



    @Bean
    public Binding bindingEmail(@Qualifier(myexchange) Exchange exchange, @Qualifier(CLASSQUEUE) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.class.#").noargs();
    }


    @Bean
    public Binding bindingSms(@Qualifier(myexchange) Exchange exchange,@Qualifier(jobQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.job.#").noargs();
    }

    @Bean
    public Binding bindingQg(@Qualifier(myexchange) Exchange exchange,@Qualifier(classinfo) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.classinfo.#").noargs();
    }

    //添加通知rabbitmq
    //添加通知消息队列
    public static final String notQueue="not.boot.queue";
    //添加通知钥匙
    public static final String notKey="not.key.queue";

    //创建添加通知消息队列
    @Bean(notQueue)
    public Queue createNotQueue(){
        Queue queue =new Queue(notQueue);
        return queue;
    }

    //将添加通知队列绑定到交换机上
    @Bean
    public Binding bindingNot(@Qualifier(myexchange) Exchange exchange , @Qualifier(notQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(notKey).noargs();
    }

    //添加调查rabbitmq
    //添加调查消息队列
    public static final String surQueue="sur.boot.queue";
    //添加调查钥匙
    public static final String surKey="sur.key.queue";

    //创建添加调查消息队列
    @Bean(surQueue)
    public Queue createSurQueue(){
        Queue queue =new Queue(surQueue);
        return queue;
    }

    //将添加调查消息对列绑定到交换机上
    @Bean
    public Binding bindingSur(@Qualifier(myexchange) Exchange exchange , @Qualifier(surQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(surKey).noargs();
    }

    //添加成绩rabbitmq
    //添加成绩消息队列
    public static final String scoQueue="sco.boot.queue";
    //添加成绩钥匙
    public static final String scoKey="sco.key.queue";

    //创建添加成绩消息队列
    @Bean(scoQueue)
    public Queue createScoQueue(){
        Queue queue =new Queue(scoQueue);
        return queue;
    }

    //将添加成绩消息对列绑定到交换机上
    @Bean
    public Binding bindingSco(@Qualifier(myexchange) Exchange exchange , @Qualifier(scoQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(scoKey).noargs();
    }

    //发送短信rabbitmq
    //发送短信消息队列
    public static final String sendQueue="send.boot.queue";
    //发送短信钥匙
    public static final String sendKey="send.key.queue";

    //创建发送短信消息队列
    @Bean(sendQueue)
    public Queue createSendQueue(){
        Queue queue =new Queue(sendQueue);
        return queue;
    }

    //将发送短信消息对列绑定到交换机上
    @Bean
    public Binding bindingSend(@Qualifier(myexchange) Exchange exchange , @Qualifier(sendQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(sendKey).noargs();
    }
}
