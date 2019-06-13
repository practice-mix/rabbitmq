package com.lecture.rabbitmq.pubilsher.lec7_overflow;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Luo Bao Ding
 * @since 2019/3/25
 */
public class OverflowDemo {
    public static final String EX_OVERFLOW_TEST = "EX-overflow-test";
    public static final String RK_OVERFLOW = "rk_overflow";
    @Autowired
    private RabbitTemplate rabbitTemplate;

    private AtomicInteger count = new AtomicInteger(0);

    @Scheduled(fixedRate = 1000, initialDelay = 500)
    public void publish() {
        try {
//            normalSend();
//            scopedOperation();
            publisherConfirm();
        } catch (AmqpException e) {
            e.printStackTrace();
        }


    }

    private void normalSend() {
        String message = "" + count.getAndIncrement();
        rabbitTemplate.convertAndSend(EX_OVERFLOW_TEST, RK_OVERFLOW, message);
        System.out.println("published message:" + message);
    }

    /**
     * <h1>framework bugs</h1>
     * <p>there are bugs in the framework where the confirm callback is not effective</p>
     * https://docs.spring.io/spring-amqp/docs/2.1.4.RELEASE/reference/#scoped-operations
     */
    public void scopedOperation() {//in the same channel

        rabbitTemplate.invoke(operations -> {
            //scoped multiple operations
            String message = "" + count.getAndIncrement();
            operations.convertAndSend(EX_OVERFLOW_TEST, RK_OVERFLOW, message);
            System.out.println("published message:" + message);

            return null;
        }, (deliveryTag, multiple) -> {//bug: not effective
            //when publisher confirm for ack
            System.out.println("[scopedOperation]publisher confirm for ack: " + deliveryTag + ", multiple=[" + multiple + "]");
        }, (deliveryTag, multiple) -> {//bug: not effective
            //when publisher confirm for nack
            System.out.println("[scopedOperation]publisher confirm for nack: " + deliveryTag + ", multiple=[" + multiple + "]");

        });
    }

    /**
     * https://docs.spring.io/spring-amqp/docs/2.1.4.RELEASE/reference/#template-confirms
     */
    public void publisherConfirm() {
        String message = "" + count.getAndIncrement();
        CorrelationData correlationData = new CorrelationData();
        correlationData.getFuture().addCallback(result -> {
            //when confirmed
            assert result != null;
            boolean isAck = result.isAck();//true: ack; false: nack, on condition that the target queue has the argument x-overflow: reject-publish

            System.out.println("[publisherConfirm] publisher " + isAck + ": " + result);
        }, ex -> {
            //when failed
            System.out.println("[publisherConfirm] published failed: " + ex.getMessage());

        });
        rabbitTemplate.convertAndSend(EX_OVERFLOW_TEST, RK_OVERFLOW, message, correlationData);
        System.out.println("published message:" + message);

    }


}
