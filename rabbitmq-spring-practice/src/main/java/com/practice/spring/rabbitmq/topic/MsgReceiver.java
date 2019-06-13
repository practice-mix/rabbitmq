package com.practice.spring.rabbitmq.topic;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.util.StopWatch;

/**
 * @author Luo Bao Ding
 * @since 2019/2/14
 */
public class MsgReceiver {


    @RabbitListener(queues = "#{autoDeleteQueue1.name}")
    public void receive1(String in) {
        receive(in, 1);
    }

    @RabbitListener(queues = "#{autoDeleteQueue2.name}")
    public void receive2(String in) {
        receive(in, 2);

    }

    private void receive(String in, int receiver) {
        StopWatch watch = new StopWatch();
        watch.start();
        System.out.println("instance " + receiver + " [x] Received '" + in + "'");
        try {
            doWork(in);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        watch.stop();

        System.out.println("instance " + receiver + " [x] Done in " + watch.getTotalTimeSeconds() + "s");


    }

    private void doWork(String in) throws InterruptedException {
        for (char ch : in.toCharArray()) {
            if (ch == '.') {
                Thread.sleep(1000);
            }
        }
    }
}
