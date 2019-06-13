package com.practice.spring.rabbitmq.topic;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/2/14
 */
public class MsgSender {


    private AmqpTemplate template;

    private TopicExchange exchange;

    AtomicInteger index = new AtomicInteger(0);

    AtomicInteger count = new AtomicInteger(0);

    private final String[] keys = {"quick.orange.rabbit", "lazy.orange.elephant", "quick.orange.fox",
            "lazy.brown.fox", "lazy.pink.rabbit", "quick.brown.fox"};


    public MsgSender(AmqpTemplate template, TopicExchange exchange) {
        this.template = template;
        this.exchange = exchange;
    }

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void send() {
        if (index.get() >= keys.length) {
            index.set(0);
        }
        String key = keys[index.getAndIncrement()];
        String message = "Hello to " + key + " " + this.count.incrementAndGet();
        this.template.convertAndSend(this.exchange.getName(), key, message);
        System.out.println(" [x] Sent '" + message + "'");

    }

}
