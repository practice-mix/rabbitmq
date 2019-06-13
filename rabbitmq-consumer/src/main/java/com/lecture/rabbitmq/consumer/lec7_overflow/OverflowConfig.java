package com.lecture.rabbitmq.consumer.lec7_overflow;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Bao Ding
 * @since 2019/3/25
 */
@Configuration
@Profile("overflow")
public class OverflowConfig {

    public static final String Q_OVERFLOW_TEST = "Q-overflow-test";
    public static final String EX_OVERFLOW_TEST = "EX-overflow-test";
    public static final String RK_OVERFLOW = "rk_overflow";

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EX_OVERFLOW_TEST, true, false, null);
    }

    @Bean
    public Queue overflowQueue() {
        Map<String, Object> args = new HashMap<>(4);
        args.put("x-max-length", 1);
        args.put("x-overflow", "reject-publish");
        return new Queue(Q_OVERFLOW_TEST, true, false, false, args);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder.bind(overflowQueue()).to(directExchange()).with(RK_OVERFLOW);
    }

    //    -----------------------------------
    @Bean
    public OverflowDemo overflowDemo() {
        return new OverflowDemo();
    }
}
