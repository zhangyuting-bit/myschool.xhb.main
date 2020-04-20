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
    public static final String qgQueue="qg.boot.queue";

    @Bean(myexchange)
    public Exchange createExchange(){
        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
    }

    @Bean(qgQueue)
    public Queue createQgQueue(){
        Queue queue=new Queue(qgQueue);
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
    public Binding bindingQg(@Qualifier(myexchange) Exchange exchange,@Qualifier(qgQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with("inform.#.qg.#").noargs();
    }


    //添加学号rabbitmq
    //添加学号消息队列
    public static final String numQueue="num.boot.queue";
    //添加学号钥匙
    public static final String numKey="num.key.queue";

    //创建添加学号消息队列
    @Bean(numQueue)
    public Queue createNumQueue(){
        Queue queue =new Queue(numQueue);
        return queue;
    }

    //将添加学号消息队列绑定到交换机上
    @Bean
    public Binding bindingNum(@Qualifier(myexchange) Exchange exchange , @Qualifier(numQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(numKey).noargs();
    }

    //删除学号rabbitmq
    //删除学号消息队列
    public static final String delQueue="num.boot.queue";
    //删除学号钥匙
    public static final String delKey="num.key.queue";

    //创建删除学号消息队列
    @Bean(delQueue)
    public Queue createDelQueue(){
        Queue queue =new Queue(delQueue);
        return queue;
    }

    //将删除学号消息队列绑定到交换机上
    @Bean
    public Binding bindingDel(@Qualifier(myexchange) Exchange exchange , @Qualifier(delQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(delKey).noargs();
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

    //发送班级信息rabbitmq
    //发送短信消息队列
    public static final String classQueue="class.boot.queue";
    //发送短信钥匙
    public static final String classKey="class.key.queue";

    //创建发送短信消息队列
    @Bean(classQueue)
    public Queue createclassQueue(){
        Queue queue =new Queue(classQueue);
        return queue;
    }

    //将发送短信消息对列绑定到交换机上
    @Bean
    public Binding bindingclass(@Qualifier(myexchange) Exchange exchange , @Qualifier(classQueue) Queue queue){
        return BindingBuilder.bind(queue).to(exchange).with(classKey).noargs();
    }
}
