package com.lecture.rabbitmq.pubilsher.lec4_fanout;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/3/18
 */
public class FanoutExchangePublisher {


    @Autowired
    private RabbitTemplate rabbitTemplate;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void publish() {
        String msg = "msg#hello world fanout" + " " + (count.getAndIncrement());
        rabbitTemplate.convertAndSend(FanoutExchangeConfig.FANOUT_EXCHANGE_NAME, "", msg);

        System.out.println("-------published msg: '" + msg + "'");
    }

}
