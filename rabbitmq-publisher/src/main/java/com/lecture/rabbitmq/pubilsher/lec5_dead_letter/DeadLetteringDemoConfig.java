package com.lecture.rabbitmq.pubilsher.lec5_dead_letter;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
@Configuration
@Profile("deadLettering")
public class DeadLetteringDemoConfig {

    @Bean
    public DeadLetteringDemo directExchangePublisher() {
        return new DeadLetteringDemo();
    }

}
