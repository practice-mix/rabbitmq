package com.lecture.rabbitmq.pubilsher.lec7_overflow;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/3/25
 */
@Configuration
@Profile("overflow")
public class OverflowConfig {

    @Bean
    public OverflowDemo overflowDemo() {
        return new OverflowDemo();
    }
}
