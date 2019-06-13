package com.lecture.rabbitmq.consumer.lec2_default;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
@Configuration
@Profile("default")
public class DefaultExchangeConfig {


    @Bean
    public Queue helloDefault() {
        return new Queue("Q-helloDefault");
    }

    @Bean
    public DefaultExchangeConsumer defaultExchangeConsumer() {
        return new DefaultExchangeConsumer();
    }


}
