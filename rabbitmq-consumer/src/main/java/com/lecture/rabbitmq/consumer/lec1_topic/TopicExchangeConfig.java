package com.lecture.rabbitmq.consumer.lec1_topic;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
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
    public TopicExchange topicExchange() {
        return new TopicExchange("EX-T-TEST");
    }

    @Bean
    @Qualifier("helloTopic")
    public Queue helloTopic() {
        return new Queue("Q-helloTopic");
    }

    @Bean
    public Binding binding(TopicExchange topicExchange, @Qualifier("helloTopic") Queue helloTopic) {
        return BindingBuilder.bind(helloTopic).to(topicExchange).with("demo.hello.*");
    }

    @Bean
    public TopicExchangeConsumer topicExchangeConsumer() {
        return new TopicExchangeConsumer();
    }
}
