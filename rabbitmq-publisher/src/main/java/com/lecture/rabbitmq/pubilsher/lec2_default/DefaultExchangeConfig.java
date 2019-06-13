package com.lecture.rabbitmq.pubilsher.lec2_default;

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
    public DefaultExchangePublisher defaultExchangePublisher() {
        return new DefaultExchangePublisher();
    }

}
