package com.practice.rabbitmq.client.tu2_direct_workqueues;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/13
 */
public class SendNewTask {

    public static final String ROUTING_KEY = "test_durable_work_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("dev.ufotosoft.com");
        factory.setUsername("admin");
        factory.setPassword("admin123");

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            boolean durable = true;
            //could remove queueDeclare but will cause msg loss
//            channel.queueDeclare(ROUTING_KEY, durable, false, false, null);

            String message = String.join(" ", args);

            // the exchange name is ""
            channel.basicPublish("", ROUTING_KEY, MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }

    }
}
