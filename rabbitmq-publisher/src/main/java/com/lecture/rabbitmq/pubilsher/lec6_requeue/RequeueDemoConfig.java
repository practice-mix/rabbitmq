package com.lecture.rabbitmq.pubilsher.lec6_requeue;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
@Configuration
@Profile("requeue")
public class RequeueDemoConfig {

    @Bean
    public RequeueDemo directExchangePublisher() {
        return new RequeueDemo();
    }

}
