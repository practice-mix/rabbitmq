package com.lecture.rabbitmq.consumer.lec3_direct;

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/2/22
 */
public class DirectExchangeConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DirectExchangeConsumer.class);
    private static final Logger lowConLogger = LoggerFactory.getLogger("lowConLogger");
    private static final Logger highConLogger = LoggerFactory.getLogger("highConLogger");
    private final static int INTERVAL = 100;


    private AtomicInteger countLowCon = new AtomicInteger(0);
    private AtomicInteger countHighCon = new AtomicInteger(0);

    private AtomicInteger lastCountLowCon = new AtomicInteger(0);
    private AtomicInteger lastCountHighCon = new AtomicInteger(0);

    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE_HELLO_1)
    public void consume1(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
        try {
            System.out.println("+++++++++received msg1: '" + payload + "'");
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            channel.basicReject(deliveryTag, false);
            LOGGER.error("--------failed to process msg1: '" + payload + "'");

        }
    }

//    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE_HELLO_1, concurrency = "5-10")
//    public void consume2(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) throws IOException {
//        System.out.println("+++++++++received msg2: '" + payload + "'");
//
//        channel.basicAck(deliveryTag, false);
//    }


    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE_HELLO_2,
            containerFactory = "lowCon"
//            concurrency = "1"
    )
    public void consume3(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        countLowCon.incrementAndGet();
        System.out.println("+++++++++received msg3: '" + payload + "'");
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RabbitListener(queues = DirectExchangeConfig.DIRECT_QUEUE_HELLO_2,
            containerFactory = "highCon"
//            concurrency = "8"
    )
    public void consume4(String payload, Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag) {
        countHighCon.incrementAndGet();

        System.out.println("+++++++++received msg4: '" + payload + "'");
        try {
            channel.basicAck(deliveryTag, false);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Scheduled(fixedDelay = INTERVAL, initialDelay = 500)
    public void statLowCon() {

        doStat(countLowCon, lastCountLowCon, lowConLogger);

    }

    @Scheduled(fixedDelay = INTERVAL, initialDelay = 500)
    public void statHighCon() {

        doStat(countHighCon, lastCountHighCon, highConLogger);
    }

    private void doStat(AtomicInteger currentCount, AtomicInteger lastCount, Logger logger) {
        if (lastCount.get() != 0) {
            int diff = currentCount.get() - lastCount.get();
            double velocity = diff * 1.0 / INTERVAL;
            logger.info(velocity + "");

        }
        lastCount.set(currentCount.get());

    }


}
