package com.example.mq.springactivemq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("active-mq")
public class ActiveMQController {

    @Autowired
    private JmsTemplate jmsTemplate;

    @GetMapping(value = "/send-1/{message}", produces = "text/html")
    public String sendMessage1(@PathVariable String message){
        jmsTemplate.convertAndSend("my-queue1", message);
        return "ok. sent " + message;
    }

    @GetMapping(value = "/send-2/{message}", produces = "text/html")
    public String sendMessage2(@PathVariable String message){
        Click click = new Click();
        click.setName(message);
        click.setCount(ThreadLocalRandom.current().nextInt(20));

        jmsTemplate.convertAndSend("my-queue2", click);
        return "ok. sent " + message;
    }
}
