package com.lecture.rabbitmq.pubilsher.lec2_default;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
public class DefaultExchangePublisher {

    @Autowired
    private RabbitTemplate template;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void publish() {
        String msg = "msg#hello world default" + " " + (count.getAndIncrement());
        template.convertAndSend("Q-helloDefault", msg);

        System.out.println("-------published msg: '" + msg + "'");
    }


}
