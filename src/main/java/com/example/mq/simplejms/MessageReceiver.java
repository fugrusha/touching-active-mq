package com.example.mq.simplejms;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

public class MessageReceiver {

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

            MessageConsumer consumer = session.createConsumer(destination);

            Message message = consumer.receive(1000);

            if (message instanceof TextMessage) {
                TextMessage textMessage = (TextMessage) message;

                System.out.println("Message: {" + textMessage.getText() + "} was received!");
            }

            session.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void startListening() {
        try {
            ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(URL);
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // create non-transactional session to send/receive messages
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            Destination destination = session.createQueue(QUEUE_NAME);

            MessageConsumer consumer = session.createConsumer(destination);
            MyListener myListener = new MyListener();
            consumer.setMessageListener(myListener);

        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    public static void stopListening(MessageConsumer consumer) {
        try {
            consumer.close();
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }


}
