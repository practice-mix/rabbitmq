package com.lecture.rabbitmq.consumer.config;

import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Luo Bao Ding
 * @since 2019/4/9
 */
@ConfigurationProperties("app.rabbit.listener")
public class LCFProperties {

    private Map<String, RabbitProperties.Listener> beanNameToListenerMap = new HashMap<>();

    public Map<String, RabbitProperties.Listener> getBeanNameToListenerMap() {
        return beanNameToListenerMap;
    }

}
