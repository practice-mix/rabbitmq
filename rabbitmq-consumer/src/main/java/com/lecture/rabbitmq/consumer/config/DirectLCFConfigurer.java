package com.lecture.rabbitmq.consumer.config;

import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.retry.MessageRecoverer;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.amqp.AbstractRabbitListenerContainerFactoryConfigurer;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.autoconfigure.amqp.RabbitRetryTemplateCustomizer;
import org.springframework.boot.context.properties.PropertyMapper;

import java.util.stream.Collectors;

/**
 * @author Luo Bao Ding
 * @since 2019/4/10
 */
public class DirectLCFConfigurer extends AbstractRabbitListenerContainerFactoryConfigurer<DirectRabbitListenerContainerFactory> {

    public DirectLCFConfigurer(ObjectProvider<MessageConverter> messageConverter,
                               ObjectProvider<MessageRecoverer> messageRecoverer,
                               ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {

        super.setMessageConverter(messageConverter.getIfUnique());
        super.setMessageRecoverer(messageRecoverer.getIfUnique());
        super.setRetryTemplateCustomizers(retryTemplateCustomizers.orderedStream().collect(Collectors.toList()));

    }

    public void configure(DirectRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory, RabbitProperties.DirectContainer config) {

        super.configure(factory, connectionFactory, config);
        PropertyMapper mapper = PropertyMapper.get();
        mapper.from(config::getConsumersPerQueue).whenNonNull().to(factory::setConsumersPerQueue);

    }

    @Deprecated
    @Override
    public void configure(DirectRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
        throw new UnsupportedOperationException();
    }
}
