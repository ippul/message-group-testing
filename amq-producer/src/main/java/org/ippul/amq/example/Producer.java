package org.ippul.amq.example;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import javax.jms.*;
import java.util.UUID;

public class Producer extends Thread {
    
    private final ConnectionFactory connectionFactory;
    
    private final Connection connection;

    private final Session session;

    private final Destination destination;

    private final String queueName;

    public Producer(final Integer threadCounter, final String amqUrl, final String username, final String password, final String queueName) throws JMSException {
        super.setName(threadCounter + "-" + queueName + "-p");
        this.queueName = queueName;
        connectionFactory = new ActiveMQConnectionFactory(amqUrl, username, password);
        connection = connectionFactory.createConnection();
        connection.start();
        session  = connection.createSession();
        destination = session.createQueue(queueName);
    }

    @Override
    public void run() {
        try {
            final MessageProducer producer = session.createProducer(destination);
            for(int count = 0; count < 10_000_000; count ++) {
                String messageBody = queueName + "-" + UUID.randomUUID().toString();
                final TextMessage message = session.createTextMessage(messageBody);
                if(!queueName.startsWith("NOT_")){
                    message.setStringProperty("JMSXGroupID", "gropuedQueue");
                }
                producer.send(message);
                System.out.println(getName() + " - message sent: " + messageBody);
                Thread.sleep(10l);
            }
            session.close();
            connection.close();
        } catch (Exception e ){
            e.printStackTrace();
        }

    }
    
}
