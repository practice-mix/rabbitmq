package com.practice.spring.rabbitmq.ha_test;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author Luo Bao Ding
 * @since 2019/2/20
 */
@Component
public class HATester implements ApplicationRunner {

    private AmqpTemplate amqpTemplate;
    private AmqpAdmin amqpAdmin;

    public HATester(AmqpTemplate amqpTemplate, AmqpAdmin amqpAdmin) {
        this.amqpTemplate = amqpTemplate;
        this.amqpAdmin = amqpAdmin;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {
//        declareDurableQueues("test-durable_queue_");
//        declareDurableQueues("test-min-masters-locator-durable_queue_");
//        declareDurableQueues("test-two-relica-min-masters-durable_queue_");


    }

    private void declareDurableQueues(String tag) {
        for (int i = 0; i < 10; i++) {
            Queue queue = new Queue(tag + i, true,
                    false, false, null);
            amqpAdmin.declareQueue(queue);
            System.out.println("[*]declare a test queue");

        }
    }

}
