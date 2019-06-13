package com.lecture.rabbitmq.consumer.lec4_fanout;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

/**
 * @author Luo Bao Ding
 * @since 2019/3/18
 */
public class FanoutExchangeConsumer {


    @RabbitListener(queues = "#{anonymousQueue.name}")
    public void consume(String msg) {
        System.out.println("+++++++++received msg1: '" + msg + "'");
    }

}
