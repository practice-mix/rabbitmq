package com.lecture.rabbitmq.consumer.config;

import org.springframework.amqp.rabbit.config.DirectRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.boot.autoconfigure.amqp.RabbitProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author Luo Bao Ding
 * @since 2019/4/9
 */
public class ListenerBeansRegistrar implements ApplicationContextAware {
    private final LCFProperties LCFProperties;

    private final ConnectionFactory connectionFactory;

    private final SimpleLCFConfigurer simpleLCFConfigurer;

    private final DirectLCFConfigurer directLCFConfigurer;

    private GenericApplicationContext applicationContext;

    public ListenerBeansRegistrar(LCFProperties LCFProperties, ConnectionFactory connectionFactory,
                                  SimpleLCFConfigurer simpleLCFConfigurer, DirectLCFConfigurer directLCFConfigurer) {
        this.LCFProperties = LCFProperties;
        this.connectionFactory = connectionFactory;
        this.simpleLCFConfigurer = simpleLCFConfigurer;
        this.directLCFConfigurer = directLCFConfigurer;
    }


    @PostConstruct
    public void registerBeanDefinitions() {

        Map<String, RabbitProperties.Listener> map = LCFProperties.getBeanNameToListenerMap();

        map.forEach((beanName, listenerProps) -> {
            if (listenerProps.getType().equals(RabbitProperties.ContainerType.SIMPLE)) {
                RabbitProperties.SimpleContainer simpleContainerProps = listenerProps.getSimple();
                BeanDefinition bd = new RootBeanDefinition(SimpleRabbitListenerContainerFactory.class,
                        () -> {
                            SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
                            simpleLCFConfigurer.configure(factory, connectionFactory, simpleContainerProps);
                            return factory;

                        });

                applicationContext.registerBeanDefinition(beanName, bd);

            } else {
                Assert.isTrue(listenerProps.getType().equals(RabbitProperties.ContainerType.DIRECT)
                        , "a listener container factory type can only be SIMPLE or DIRECCT");

                RabbitProperties.DirectContainer directProps = listenerProps.getDirect();
                BeanDefinition bd = new RootBeanDefinition(DirectRabbitListenerContainerFactory.class, () -> {
                    DirectRabbitListenerContainerFactory factory = new DirectRabbitListenerContainerFactory();
                    directLCFConfigurer.configure(factory, connectionFactory, directProps);

                    return factory;
                });
                applicationContext.registerBeanDefinition(beanName, bd);
            }


        });
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

        this.applicationContext = (GenericApplicationContext) applicationContext;
    }
}
