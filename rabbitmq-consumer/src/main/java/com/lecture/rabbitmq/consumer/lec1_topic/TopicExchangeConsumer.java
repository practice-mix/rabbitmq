package com.lecture.rabbitmq.consumer.lec1_topic;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
public class TopicExchangeConsumer {
    @RabbitListener(queues = "Q-helloTopic")
    public void receive(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
            throws IOException {
        System.out.print(".");

        channel.basicAck(tag, false);
    }

    @RabbitListener(queues = "Q-helloTopic")
    public void consume1(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("consume1+++++++++received msg: '" + msg + "'");
        channel.basicAck(tag, false);

    }

//    @RabbitListener(queues = "Q-helloTopic")
    public void consume2(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("consume2+++++++++received msg: '" + msg + "'");
        channel.basicAck(tag, false);

    }

//    @RabbitListener(queues = "Q-helloTopic")
    public void consume3(String msg, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag) throws IOException {
        System.out.println("consume3+++++++++received msg: '" + msg + "'");
        channel.basicAck(tag, false);

    }

    /**
     * consumer acknowledgement
     */
//    @RabbitListener(queues = "Q-helloTopic")
    public void consume4(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
            throws IOException {
        System.out.println("consume4+++++++++received msg: '" + payload + "'");

        channel.basicAck(tag, false);
        // reject and requeue the delivery
//        channel.basicReject(tag,true);
        // nack and requeue the delivery, multiple nack
//        channel.basicNack(tag,false,true);
    }


//    @RabbitListener(queues = "Q-helloTopic")
    public void receive2(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long tag)
            throws IOException {
        System.out.print(".");

        channel.basicAck(tag, false);
    }

}
