package com.lecture.rabbitmq.consumer.lec6_requeue;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/3/20
 */
@Configuration
@Profile("requeue")
public class RequeueConfig {
    public static final String Q_REQUEUE_DEMO_QUEUE = "Q-myRequeueDemoQueue";
    public static final String EX_MY_REQUEUE_DEMO_EXCHANGE = "EX-myRequeueDemoExchange";
    public static final String RK_MY_REQUEUE_DEMO_BINDING = "rk-myRequeueDemoBinding";

    @Bean
    public DirectExchange myRequeueDemoExchange(){
        return new DirectExchange(EX_MY_REQUEUE_DEMO_EXCHANGE);

    }

    @Bean
    public Queue myRequeueDemoQueue(){
        return new Queue(Q_REQUEUE_DEMO_QUEUE);
    }

    @Bean
    public Binding myRequeueDemoBinding(){
        return BindingBuilder.bind(myRequeueDemoQueue()).to(myRequeueDemoExchange()).with(RK_MY_REQUEUE_DEMO_BINDING);
    }

    @Bean
    public RequeueDemo requeueDemo(){
        return new RequeueDemo();
    }
}

