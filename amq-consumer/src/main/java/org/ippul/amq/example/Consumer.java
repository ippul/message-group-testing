package org.ippul.amq.example;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import javax.jms.*;

public class Consumer extends Thread {
    
    private final ConnectionFactory connectionFactory;
    
    private final Connection connection;

    private final Session session;

    private final Destination destination;

    private final String queueName;

    public Consumer(final Integer threadCounter, final String amqUrl, final String username, final String password, final String queueName) throws JMSException {
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
            final MessageConsumer consumer = session.createConsumer(destination);
            for(int count = 0; count<10_000_000; count ++) {
                final Message message = consumer.receive(10000);
                String messageBody = message.getBody(String.class);
                System.out.println(getName() + " - " + messageBody);
                message.acknowledge();
                Thread.sleep(1000l);
            }
            session.close();
            connection.close();
        } catch (Exception e ){
            e.printStackTrace();
        }
    }
}
