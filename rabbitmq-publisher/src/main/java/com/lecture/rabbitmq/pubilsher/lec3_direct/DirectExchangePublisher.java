package com.lecture.rabbitmq.pubilsher.lec3_direct;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
public class DirectExchangePublisher {
    public static final String EX_DIRECT_TEST = "EX-DIRECT-TEST";
    public static final String ROUTING_KEY = "rk-direct-a";

    @Autowired
    private RabbitTemplate template;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 3000, initialDelay = 500)
    public void publish() {
        String msg = "msg#hello world direct" + " " + (count.getAndIncrement());
        template.convertAndSend(EX_DIRECT_TEST, ROUTING_KEY, msg);

        System.out.println("-------published msg: '" + msg + "'");
    }


}
