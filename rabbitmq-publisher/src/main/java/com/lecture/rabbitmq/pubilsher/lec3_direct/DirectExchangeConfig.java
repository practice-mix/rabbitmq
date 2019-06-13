package com.lecture.rabbitmq.pubilsher.lec3_direct;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
@Configuration
@Profile("direct")
public class DirectExchangeConfig {

    @Bean
    public  DirectExchangePublisher directExchangePublisher(){
        return new DirectExchangePublisher();
    }

}
