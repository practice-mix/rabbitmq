package com.lecture.rabbitmq.consumer.config;

import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luo Bao Ding
 * @since 2019/4/9
 */
@Configuration
@EnableConfigurationProperties(LCFProperties.class)
public class AppRabbitmqConsumerConcurrencyConfig {

//    @Bean
//    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
//        return new Jackson2JsonMessageConverter();
//    }

    @Bean
    public SimpleLCFConfigurer simpleLCFConfigurer(ObjectProvider<MessageConverter> messageConverter,
                                                   ObjectProvider<MessageRecoverer> messageRecoverer,
                                                   ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {
        return new SimpleLCFConfigurer(messageConverter, messageRecoverer, retryTemplateCustomizers);
    }

    @Bean
    public DirectLCFConfigurer directLCFConfigurer(ObjectProvider<MessageConverter> messageConverter,
                                                   ObjectProvider<MessageRecoverer> messageRecoverer,
                                                   ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {

        return new DirectLCFConfigurer(messageConverter, messageRecoverer, retryTemplateCustomizers);
    }

    @Bean
    public ListenerBeansRegistrar listenerBeansRegistrar(LCFProperties properties, ConnectionFactory connectionFactory,
                                                         SimpleLCFConfigurer simpleLCFConfigurer, DirectLCFConfigurer directLCFConfigurer) {
        return new ListenerBeansRegistrar(properties, connectionFactory, simpleLCFConfigurer, directLCFConfigurer);
    }


}
