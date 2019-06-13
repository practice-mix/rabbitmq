package com.practice.spring.rabbitmq.topic;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author Luo Bao Ding
 * @since 2019/2/14
 */
@Configuration
@Profile("topic")
public class MQConfig {

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange("animal-topic");
    }


    @Bean
    public MsgSender msgSender(AmqpTemplate template, TopicExchange exchange) {
        return new MsgSender(template, exchange);
    }

    private static class ReceiverConfig {

        @Bean
        @Qualifier("autoDeleteQueue1")
        public Queue autoDeleteQueue1() {
            return new AnonymousQueue();
        }

        @Bean
        @Qualifier("autoDeleteQueue2")
        public Queue autoDeleteQueue2() {
            return new AnonymousQueue();
        }

        @Bean
        public Binding binding1a(@Qualifier("autoDeleteQueue1") Queue autoDeleteQueue1, TopicExchange topicExchange) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topicExchange).with("*.orange.*");

        }

        @Bean
        public Binding binding1b(@Qualifier("autoDeleteQueue1") Queue autoDeleteQueue1, TopicExchange topicExchange) {
            return BindingBuilder.bind(autoDeleteQueue1).to(topicExchange).with("*.*.rabbit");
        }

        @Bean
        public Binding binding2(@Qualifier("autoDeleteQueue2") Queue autoDeleteQueue2, TopicExchange topicExchange) {
            return BindingBuilder.bind(autoDeleteQueue2).to(topicExchange).with("lazy.#");
        }


        @Bean
        public MsgReceiver msgReceiver() {
            return new MsgReceiver();
        }
    }

}
