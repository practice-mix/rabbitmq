package com.practice.rabbitmq.client.tu3_fanout_pubsub;

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

    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws IOException, TimeoutException {

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("dev.ufotosoft.com");

        try (Connection connection = factory.newConnection()) {

            Channel channel = connection.createChannel();

            channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.FANOUT);

            String message = args.length < 1 ? "info: hello world" : String.join(" ", args);
            channel.basicPublish(EXCHANGE_NAME, "", null,
                    message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");


        }

    }
}
