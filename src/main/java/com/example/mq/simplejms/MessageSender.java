package com.example.mq.simplejms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageSender {

    // tcp://localhost:61616
    private static final String URL = ActiveMQConnection.DEFAULT_BROKER_URL;

    private static final String QUEUE_NAME = "simple-jms-service";

    public static void main(String[] args) {
        System.out.println("Broker URL = " + URL);

        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // create non-transactional session to send/receive messages
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(QUEUE_NAME);

            MessageProducer producer = session.createProducer(destination);

            TextMessage message = session.createTextMessage("Hello world!");

            producer.send(message);
            System.out.println("Message: {" + message.getText() + "} was sent!");

            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
