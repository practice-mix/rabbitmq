package com.lecture.rabbitmq.consumer.lec5_dead_letter;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author Luo Bao Ding
 * @since 2019/3/20
 */
public class DeadLetteringDemo {
    private AtomicBoolean isProduceDeadLetter = new AtomicBoolean(false);
    public static final boolean IS_REQUEUE = false;

    @RabbitListener(queues = DeadLetteringConfig.DEAD_LETTER_QUEUE)
    public void consumeDeadLetter(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        System.out.println("-----------dead letter: " + payload);//just log
        channel.basicAck(deliveryTag, true);
    }


    /**
     * randomly produce dead-letter
     */
    @RabbitListener(queues = DeadLetteringConfig.DEAD_LETTER_ENABLED_QUEUE)
    public void consume(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {

        if (isProduceDeadLetter.getAndSet(!isProduceDeadLetter.get())) {
            System.out.println("-----------reject msg " + (IS_REQUEUE ? "with" : "without") + " requeue : '" + payload + "'");
            //produce dead-letter
            channel.basicReject(deliveryTag, IS_REQUEUE);
        } else {
            System.out.println("++++++++++++received msg: '" + payload + "'");
            channel.basicAck(deliveryTag, false);
        }

    }
}
