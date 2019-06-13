package com.lecture.rabbitmq.pubilsher.lec1_topic;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
@Configuration
@Profile("topic")
public class TopicExchangeConfig {

    @Bean
    public  TopicExchangePublisher topicExchangePublisher(){
        return new TopicExchangePublisher();
    }

}
