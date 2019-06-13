package com.practice.rabbitmq.client.tu5_topic_pubsub;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/14
 */
public class ReceiveLogsTopic {

    public static final String EXCHANGE_NAME = "logs_topic";

    public static void main(String[] args) throws IOException, TimeoutException {

        if (args.length < 1) {
            System.err.println("Usage: ReceiveLogsTopic [routingKey]...");
            System.exit(1);
        }

        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("dev.ufotosoft.com");

        Connection connection = factory.newConnection();

        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);

        String queueName = channel.queueDeclare().getQueue();

        //bind
        for (String routingKey : args) {
            channel.queueBind(queueName, EXCHANGE_NAME, routingKey);
        }
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        channel.basicConsume(queueName, true, (consumerTag, delivery) -> {
            byte[] body = delivery.getBody();
            String message = new String(body, StandardCharsets.UTF_8);

            System.out.println(" [x] Received '" + delivery.getEnvelope().getRoutingKey()
                    + "':'" + message + "'");

        }, consumerTag -> {
        });

    }
}
