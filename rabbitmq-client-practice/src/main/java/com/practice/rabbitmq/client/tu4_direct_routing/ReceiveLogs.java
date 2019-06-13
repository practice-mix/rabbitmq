package com.practice.rabbitmq.client.tu4_direct_routing;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/13
 */
public class ReceiveLogs {
    public static final String EXCHANGE_NAME = "direct_logs";

    public static void main(String[] args) throws IOException, TimeoutException {
        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsDirect  [info] [warn] [error] ");
            System.exit(1);
        }
        String[] severities = new String[]{"info", "warn", "error"};
        List<String> list = Arrays.asList(severities);
        for (String arg : args) {
            if (!list.contains(arg)) {
                System.err.println("Usage: ReceiveLogsDirect  [info] [warn] [error] ");
                System.exit(2);
            }
        }
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("dev.ufotosoft.com");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);

        String queueName = channel.queueDeclare().getQueue();
        //bind
        for (String severity : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, severity);
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicConsume(queueName, true, (consumerTag, delivery) -> {
            byte[] body = delivery.getBody();
            String message = new String(body, StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");

        }, consumerTag -> {
        });

    }
}
