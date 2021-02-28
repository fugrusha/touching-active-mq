package com.example.mq.springactivemq;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer {

    @JmsListener(destination = "my-queue1")
    public void processMessage(String message) {
        System.out.println(message);
    }

    @JmsListener(destination = "my-queue2")
    @SendTo("my-queue-answer")
    public String processClickObject(Click click) {
        System.out.println(click);
        return "I've received = " + click.getName();
    }
}
