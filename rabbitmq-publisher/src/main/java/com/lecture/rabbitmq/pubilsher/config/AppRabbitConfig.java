package com.lecture.rabbitmq.pubilsher.config;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ShutdownSignalException;
import org.springframework.amqp.rabbit.connection.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Luo Bao Ding
 * @since 2019/2/25
 */
@Configuration
public class AppRabbitConfig {

    public static final String appName = "spring.application.name";

    @Bean
    public ConnectionNameStrategy connectionNameStrategy() {
        return new SimplePropertyValueConnectionNameStrategy(appName);
    }

    /**
     * To avoid deadlocked connections, it is generally recommended to use
     * a separate connection for publishers and consumers (except when a publisher
     * is participating in a consumer transaction)
     */
    @Bean
    public Object configRabbitTemplate(RabbitTemplate rabbitTemplate) {
        rabbitTemplate.setUsePublisherConnection(true);
//        configTemplateConfirm(rabbitTemplate);
//        configReplyTo(rabbitTemplate);

        return null;
    }

    /**
     * template publisher confirm and publisher return<p>
     * only one template publisher confirm and only one template publisher return
     * </p>
     * https://docs.spring.io/spring-amqp/docs/2.1.4.RELEASE/reference/#template-confirms
     */
    private void configTemplateConfirm(RabbitTemplate rabbitTemplate) {

//       custom a ReturnCallback
        //Only one ReturnCallback is supported by each RabbitTemplate
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            System.out.println("[globalCallback] ReturnCallback: message = [" + message + "], replyCode = [" + replyCode + "], replyText = [" + replyText + "], exchange = [" + exchange + "], routingKey = [" + routingKey + "]");
        });
        //custom ConfirmCallback
        //Only one ConfirmCallback is supported by a RabbitTemplate
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            System.out.println("[globalCallback] ConfirmCallback: ack = [" + ack + "], " + (ack ? "" : cause));
        });
        rabbitTemplate.setRecoveryCallback(retryContext -> {
            System.out.println("[globalCallback] RecoveryCallback, retry count: "+retryContext.getRetryCount());

            return null;
        });

    }

    private void configReplyTo(RabbitTemplate rabbitTemplate) {
        //Direct reply-to
        rabbitTemplate.setUseDirectReplyToContainer(true);
        rabbitTemplate.setReplyErrorHandler(t -> {
            System.out.println("reply error: " + t.getMessage());
        });
    }

    @Bean
    public Object configConnectionFactory(CachingConnectionFactory rabbitConnectionFactory) {
        addListener(rabbitConnectionFactory);
//        setSimplePublisherConfirms(rabbitConnectionFactory);

        return null;
    }

    /**
     * <h1>not recommended</h1><p>
     * https://docs.spring.io/spring-amqp/docs/2.1.4.RELEASE/reference/#scoped-operations
     * </p>
     */
    private void setSimplePublisherConfirms(CachingConnectionFactory rabbitConnectionFactory) {

        rabbitConnectionFactory.setPublisherConfirms(false);
        System.out.println("switch to use simplePublisherConfirms");
        rabbitConnectionFactory.setSimplePublisherConfirms(true);

    }

    /**
     * https://docs.spring.io/spring-amqp/docs/2.1.4.RELEASE/reference/#connection-channel-listeners
     */
    private void addListener(CachingConnectionFactory rabbitConnectionFactory) {
        rabbitConnectionFactory.addConnectionListener(new ConnectionListener() {
            @Override
            public void onCreate(Connection connection) {
                System.out.println("Connection created");

            }

            @Override
            public void onClose(Connection connection) {
                System.out.println("Connection closed");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println("Connection shutdown");

            }

        });
        rabbitConnectionFactory.addChannelListener(new ChannelListener() {
            @Override
            public void onCreate(Channel channel, boolean transactional) {
                System.out.println("Channel created");
            }

            @Override
            public void onShutDown(ShutdownSignalException signal) {
                System.out.println("Channel shutdown");
            }

        });
    }
}
