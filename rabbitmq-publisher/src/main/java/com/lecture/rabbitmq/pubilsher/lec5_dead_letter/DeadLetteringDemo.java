package com.lecture.rabbitmq.pubilsher.lec5_dead_letter;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/3/18
 */
public class DeadLetteringDemo {
    public static final String EX_NORMAL_EXCHANGE = "EX-normalExchange";
    public static final String RK_NORMAL_ROUTING_KEY = "rk-normalRoutingKey";


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void publish() {
        String msg = "msg#dead-lettering demo:" + " " + (count.getAndIncrement());
        rabbitTemplate.convertAndSend(EX_NORMAL_EXCHANGE, RK_NORMAL_ROUTING_KEY, msg);

        System.out.println("-------published msg: '" + msg + "'");
    }

}
