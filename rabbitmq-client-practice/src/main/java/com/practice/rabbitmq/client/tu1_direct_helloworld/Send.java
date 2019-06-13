package com.practice.rabbitmq.client.tu1_direct_helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/13
 */
public class Send {


    private static final String ROUTING_KEY = "transient-hello";

    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("dev.ufotosoft.com");
        factory.setUsername("admin");
        factory.setPassword("admin123");
        try (Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();

            //could remove queueDeclare but will cause msg loss
            channel.queueDeclare(ROUTING_KEY, false, false, false, null);
            String message = "hello world";
            // the exchange name is ""
            channel.basicPublish("", ROUTING_KEY, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");

        }


    }
}
