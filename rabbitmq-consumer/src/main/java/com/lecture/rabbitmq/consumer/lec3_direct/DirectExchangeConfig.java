package com.lecture.rabbitmq.consumer.lec3_direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * directExchange ribbon-routes a message to only one queue out of multiple queues <p/>
 *
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
@Configuration
@Profile("direct")
public class DirectExchangeConfig {

    public static final String DIRECT_QUEUE_HELLO_1 = "Q-helloDirect1";
    public static final String DIRECT_QUEUE_HELLO_2 = "Q-helloDirect2";
    public static final String EX_DIRECT_TEST = "EX-DIRECT-TEST";
    public static final String ROUTING_KEY = "rk-direct-a";

    @Bean
    @Qualifier("directExchange")
    public DirectExchange directExchange() {
        return new DirectExchange(EX_DIRECT_TEST);
    }


    @Bean
    @Qualifier("helloDirect1")
    public Queue helloDirect1() {
        return new Queue(DIRECT_QUEUE_HELLO_1);
    }

    @Bean
    @Qualifier("helloDirect2")
    public Queue helloDirect2() {
        return new Queue(DIRECT_QUEUE_HELLO_2);
    }

    @Bean
    public Binding binding1(@Qualifier("directExchange") DirectExchange exchange, @Qualifier("helloDirect1") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public Binding binding2(@Qualifier("directExchange") DirectExchange exchange, @Qualifier("helloDirect2") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(ROUTING_KEY);
    }

    @Bean
    public DirectExchangeConsumer directExchangeConsumer() {
        return new DirectExchangeConsumer();
    }


}
