package com.lecture.rabbitmq.consumer.lec6_requeue;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/3/20
 */
public class RequeueDemo {
    public static final org.slf4j.Logger LOGGER = org.slf4j.LoggerFactory.getLogger(RequeueDemo.class);
    private AtomicInteger redeliveryCount = new AtomicInteger(0);

    @RabbitListener(queues = RequeueConfig.Q_REQUEUE_DEMO_QUEUE)
    public void consume1(int payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            if (payload % 2 == 0) {
                LOGGER.info("+++++++++error processing msg1: '" + payload + "'");
                throw new RuntimeException("manual exception");
            } else {
                LOGGER.info("+++++++++successful processing msg1: '" + payload + "'");
                channel.basicAck(deliveryTag, false);

            }
        } catch (Exception e) {

            if (redeliveryCount.getAndIncrement() < 3) {//requeued
                LOGGER.warn("------------requeue msg:" + payload);
                channel.basicReject(deliveryTag, true);
                //or
//            channel.basicNack(deliveryTag,false,true);
            } else {//discarded/dead-lettered
                LOGGER.error("------------failed processing msg:" + payload);
                channel.basicReject(deliveryTag, false);
//or
//                channel.basicNack(deliveryTag,false,false);
                redeliveryCount.set(0);

            }
        }
    }

}
