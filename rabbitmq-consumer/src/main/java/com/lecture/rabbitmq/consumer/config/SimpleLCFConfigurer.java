package com.lecture.rabbitmq.consumer.config;

import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
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
 * @since 2019/4/9
 */
public class SimpleLCFConfigurer extends AbstractRabbitListenerContainerFactoryConfigurer<SimpleRabbitListenerContainerFactory> {


    public SimpleLCFConfigurer(ObjectProvider<MessageConverter> messageConverter,
                               ObjectProvider<MessageRecoverer> messageRecoverer,
                               ObjectProvider<RabbitRetryTemplateCustomizer> retryTemplateCustomizers) {

        super.setMessageConverter(messageConverter.getIfUnique());
        super.setMessageRecoverer(messageRecoverer.getIfUnique());
        super.setRetryTemplateCustomizers(retryTemplateCustomizers.orderedStream().collect(Collectors.toList()));
    }

    public void configure(SimpleRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory, RabbitProperties.SimpleContainer config) {
        super.configure(factory, connectionFactory, config);

        PropertyMapper mapper = PropertyMapper.get();
        mapper.from(config::getConcurrency).whenNonNull().to(factory::setConcurrentConsumers);
        mapper.from(config::getMaxConcurrency).whenNonNull().to(factory::setMaxConcurrentConsumers);
        mapper.from(config::getTransactionSize).whenNonNull().to(factory::setTxSize);

    }

    @Deprecated
    @Override
    public void configure(SimpleRabbitListenerContainerFactory factory, ConnectionFactory connectionFactory) {
        throw new UnsupportedOperationException();
    }

}
