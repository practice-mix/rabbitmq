package com.lecture.rabbitmq.consumer.lec7_overflow;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;

/**
 * @author Luo Bao Ding
 * @since 2019/3/25
 */
public class OverflowDemo {


    @RabbitListener(queues = OverflowConfig.Q_OVERFLOW_TEST)
    public void rabbitListen(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {

        System.out.println("+++++++++++++ received message: " + payload);
        channel.basicAck(deliveryTag, false);

        // TODO: 2019/3/25

    }
}
