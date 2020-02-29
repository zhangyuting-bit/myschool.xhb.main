package com.zb.config;

import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {
//    public static final String emailQueue="email.boot.queue";
//    public static final String smsQueue="sms.boot.queue";
//    public static final String myexchange="myexchange.boot";
//    public static final String qgQueue="qg.boot.queue";
//
//    @Bean(myexchange)
//    public Exchange createExchange(){
//        return ExchangeBuilder.topicExchange(myexchange).durable(true).build();
//    }
//
//    @Bean(qgQueue)
//    public Queue createQgQueue(){
//        Queue queue=new Queue(qgQueue);
//        return queue;
//    }
//
//    @Bean(emailQueue)
//    public Queue createEmailQueue(){
//        Queue queue=new Queue(emailQueue);
//        return queue;
//    }
//
//    @Bean(smsQueue)
//    public Queue createSmsQueue(){
//        Queue queue=new Queue(smsQueue);
//        return queue;
//    }
//
//
//
//    @Bean
//    public Binding bindingEmail(@Qualifier(myexchange) Exchange exchange,@Qualifier(emailQueue) Queue queue){
//        return BindingBuilder.bind(queue).to(exchange).with("inform.#.email.#").noargs();
//    }
//
//
//    @Bean
//    public Binding bindingSms(@Qualifier(myexchange) Exchange exchange,@Qualifier(smsQueue) Queue queue){
//        return BindingBuilder.bind(queue).to(exchange).with("inform.#.sms.#").noargs();
//    }
//
//    @Bean
//    public Binding bindingQg(@Qualifier(myexchange) Exchange exchange,@Qualifier(qgQueue) Queue queue){
//        return BindingBuilder.bind(queue).to(exchange).with("inform.#.qg.#").noargs();
//    }
}
