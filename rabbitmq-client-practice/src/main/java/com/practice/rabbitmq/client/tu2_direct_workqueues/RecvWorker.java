package com.practice.rabbitmq.client.tu2_direct_workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/13
 */
public class RecvWorker {
    public static final String QUEUE_NAME = "test_durable_work_queue";


    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("dev.ufotosoft.com");
        factory.setUsername("admin");
        factory.setPassword("admin123");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        boolean durable = true;
        channel.queueDeclare(QUEUE_NAME, durable, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicQos(1);
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, (consumerTag, message) -> {
            byte[] body = message.getBody();
            String msg = new String(body, StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + msg + "'");
            try {
                doWork(msg);
            } finally {
                System.out.println(" [x] Done");

                channel.basicAck(message.getEnvelope().getDeliveryTag(), false);
            }

        }, consumerTag -> {
        });


    }

    private static void doWork(String msg) {

        System.out.println(" [x] Work begin");
        System.out.println(" [x] Do work...");
        for (char i : msg.toCharArray()) {
            if (i == '.') {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        }
        System.out.println(" [x] Work done");
    }
}
