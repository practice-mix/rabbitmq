package com.lecture.rabbitmq.pubilsher.lec1_topic;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
public class TopicExchangePublisher {

    @Autowired
    private RabbitTemplate template;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void publish() {
        String msg = "msg#hello world topic" + " " + (count.getAndIncrement());
        template.convertAndSend("EX-T-TEST", "demo.hello.topic", msg);
//
//        CorrelationData correlationData = new CorrelationData("pubConfirm");
//        template.convertAndSend("EX-T-TEST", "demo.hello.topic", msg, correlationData);

        System.out.println("-------published msg: '" + msg + "'");
    }

}
