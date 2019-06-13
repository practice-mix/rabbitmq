package com.lecture.rabbitmq.consumer.lec4_fanout;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/3/18
 */
@Configuration
@Profile("fanout")
public class FanoutExchangeConfig {


    public static final  String FANOUT_EXCHANGE_NAME = "rabbitmq-publisher.fanout.demo";

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(FANOUT_EXCHANGE_NAME);
    }

    @Bean
    @Qualifier("anonymousQueue")
    public Queue anonymousQueue() {
        return new AnonymousQueue();
    }

    @Bean
    public Binding binding(FanoutExchange exchange, @Qualifier("anonymousQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange);
    }


    @Bean
    public FanoutExchangeConsumer fanoutExchangeConsumer() {
        return new FanoutExchangeConsumer();
    }


}
