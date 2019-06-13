package com.lecture.rabbitmq.pubilsher.lec4_fanout;

import org.springframework.amqp.core.FanoutExchange;
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
    public FanoutExchangePublisher  fanoutExchangePublisher(){
        return new FanoutExchangePublisher();
    }



}
