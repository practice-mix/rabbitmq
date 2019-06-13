package com.lecture.rabbitmq.consumer.lec5_dead_letter;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.Map;

/**
 * 死信机制指: 死信被所在队列发布到指定交换器, 此队列充当消息发布者, 此交换器称为死信交换器, 凡绑定到死信交换器的队列称为死信队列.<p>
 *     死信交换器是普通的交换器, 死信队列也为普通的队列. 死信机制是队列的一个功能, 其他该怎么做仍然怎么做.
 * </p>
 * <p>
 *    死信指: 过期的消息\被rejected或nack的消息\队列满后被新消息顶出的头消息(默认被顶出)
 * </p>
 *
 * <p>
 *  启用死信机制:   仅需在创建队列时增加指定死信交换器和死信路由键, 其他与普通情况无异. 消息被标为死信后其路由键则被所指定的死信路由键而覆盖
 * </p>
 *
 *
 *
 * @author Luo Bao Ding
 * @since 2019/3/20
 */
@Configuration
@Profile("deadLettering")
public class DeadLetteringConfig {
    public static final String DEAD_LETTER_ROUTING_KEY = "my-dead-letter-routing-key";
    public static final String DEAD_LETTER_EXCHANGE_NAME = "my-dead-letter-exchange.direct";
    public static final String DEAD_LETTER_QUEUE = "Q-deadLetterQueue";

    public static final String DEAD_LETTER_ENABLED_QUEUE = "Q-dead-letter-enabled-queue";
    public static final String EX_NORMAL_EXCHANGE = "EX-normalExchange";
    public static final String RK_NORMAL_ROUTING_KEY = "rk-normalRoutingKey";

    /**
     * 创建死信交换器
     */
    @Bean
    @Qualifier("deadLetterExchange")
    public DirectExchange deadLetterExchange() {
        return new DirectExchange(DEAD_LETTER_EXCHANGE_NAME);
    }

    /**
     * 创建死信队列
     */
    @Bean
    @Qualifier("deadLetterQueue")
    public Queue deadLetterQueue() {
        return new Queue(DEAD_LETTER_QUEUE);
    }

    /**
     * 绑定死信队列到死信交换器
     */
    @Bean
    public Binding deadLetterBinding(@Qualifier("deadLetterExchange") DirectExchange deadLetterExchange,
                                     @Qualifier("deadLetterQueue") Queue deadLetterQueue) {
        return BindingBuilder.bind(deadLetterQueue)
                .to(deadLetterExchange).with(DEAD_LETTER_ROUTING_KEY);

    }
//    -----------------------------

    /**
     * 创建业务交换器
     */
    @Bean
    public DirectExchange normalExchange() {
        return new DirectExchange(EX_NORMAL_EXCHANGE);
    }

    /**
     * 创建业务队列时指定死信交换器和死信的路由键
     */
    @Bean
    @Qualifier("deadLetterEnabledQueue")
    public Queue deadLetterEnabledQueue() {
        Map<String, Object> arguments = new HashMap<>(8);
        //指定死信交换器
        arguments.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE_NAME);
        //指定死信的路由键
        arguments.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        return new Queue(DEAD_LETTER_ENABLED_QUEUE, true, false, false, arguments);
    }

    @Bean
    public Binding normalBinding() {
        return BindingBuilder.bind(deadLetterEnabledQueue()).to(normalExchange()).with(RK_NORMAL_ROUTING_KEY);

    }


    //    -------------------
    @Bean
    DeadLetteringDemo deadLetterDemo() {
        return new DeadLetteringDemo();
    }
}
