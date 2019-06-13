package com.lecture.rabbitmq.pubilsher.lec6_requeue;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/3/18
 */
public class RequeueDemo {
    public static final String EX_MY_REQUEUE_DEMO_EXCHANGE = "EX-myRequeueDemoExchange";
    public static final String RK_MY_REQUEUE_DEMO_BINDING = "rk-myRequeueDemoBinding";

    @Autowired
    private RabbitTemplate rabbitTemplate;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void publish() {
        int payload = count.getAndIncrement();
        rabbitTemplate.convertAndSend(EX_MY_REQUEUE_DEMO_EXCHANGE, RK_MY_REQUEUE_DEMO_BINDING, payload);

        System.out.println("-------published msg: " + payload);
    }

}
