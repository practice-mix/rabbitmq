package com.practice.rabbitmq.client.tu4_direct_routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/13
 */
public class SendLog {
    public static final String EXCHANGE_NAME = "direct_logs";


    public static void main(String[] args) throws IOException, TimeoutException, InterruptedException {

        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("dev.ufotosoft.com");

        try (Connection connection = factory.newConnection()) {
            Channel channel = connection.createChannel();
            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

            String[] severities = new String[]{"info", "warn", "error"};
            int length = severities.length;
            for (int i = 0; i < 10; i++) {
                String message = "i am logs " + i;
                int index = i % length;
                String severity = severities[index];
                channel.basicPublish(EXCHANGE_NAME, severity, null, message.getBytes());
                System.out.println(" [x] Sent '" + severity + "':'" + message + "'");

                Thread.sleep(1000);
            }


        }

    }
}
