package com.lecture.rabbitmq.consumer.lec2_default;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
public class DefaultExchangeConsumer {


    @RabbitListener(queues = "Q-helloDefault")
    public void consume(String msg) {
        System.out.println("+++++++++received msg: '" + msg + "'");
    }


}
