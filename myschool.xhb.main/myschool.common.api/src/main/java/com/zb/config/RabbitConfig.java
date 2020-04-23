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
    //申请退出班级列
    public static final String outclass="outclass.queue";
    //刘志强专用
    public static final String outclassinfo="outclassinfo.queue";

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
    @Bean(outclass)
    public Queue createoutclassQueue(){
        Queue queue=new Queue(outclass);
        return queue;
    }
    @Bean(outclassinfo)
    public Queue createoutclassinfoQueue(){
        Queue queue=new Queue(outclassinfo);
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
    @Bean
    public Binding bindingout(@Qualifier(myexchange) Exchange exchange,@Qualifier(outclass) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.outclass.#").noargs();
    }
    @Bean
    public Binding bindingoutclassinfo(@Qualifier(myexchange) Exchange exchange,@Qualifier(outclassinfo) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.outclassinfo.#").noargs();
    }

    //添加通知rabbitmq
    //上传通知消息队列
    public static final String nocQueue="noc.boot.queue";
    //上传文件钥匙
    public static final String nocKey="noc.key.queue";

    //交换机配置
//    @Bean(myexchange)
//    public Exchange createNocExchange(){
//        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
//    }


    //创建上传通知消息队列
    @Bean(nocQueue)
    public Queue createNocQueue(){
        Queue queue =new Queue(nocQueue);
        return queue;
    }

    //将上传通知消息对列绑定到交换机上
    @Bean
    public Binding bindingNoc(@Qualifier(myexchange) Exchange exchange , @Qualifier(nocQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(nocKey).noargs();
    }

    //添加成绩rabbitmq
    //上传成绩消息队列
    public static final String scoQueue="sco.boot.queue";
    //上传成绩钥匙
    public static final String scoKey="sco.key.queue";

    //交换机配置
//    @Bean(myexchange)
//    public Exchange createScoExchange(){
//        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
//    }

    //创建上传成绩消息队列
    @Bean(scoQueue)
    public Queue createScoQueue(){
        Queue queue =new Queue(scoQueue);
        return queue;
    }

    //将上传成绩消息对列绑定到交换机上
    @Bean
    public Binding bindingSco(@Qualifier(myexchange) Exchange exchange , @Qualifier(scoQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(scoKey).noargs();
    }

    //添加调查rabbitmq
    //上传调查消息队列
    public static final String surQueue="sur.boot.queue";
    //上传调查钥匙
    public static final String surKey="sur.key.queue";

    //交换机配置
//    @Bean(myexchange)
//    public Exchange createSurExchange(){
//        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
//    }

    //创建上传调查消息队列
    @Bean(surQueue)
    public Queue createSurQueue(){
        Queue queue =new Queue(surQueue);
        return queue;
    }

    //将上传文件消息对列绑定到交换机上
    @Bean
    public Binding bindingDoc(@Qualifier(myexchange) Exchange exchange , @Qualifier(surQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(surKey).noargs();
    }
}
