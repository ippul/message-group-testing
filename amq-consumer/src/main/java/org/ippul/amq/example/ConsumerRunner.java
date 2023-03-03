package org.ippul.amq.example;

import org.apache.activemq.artemis.jms.client.ActiveMQConnectionFactory;
import javax.jms.*;

public class ConsumerRunner {
    
    public static void main(String[] args) throws Exception {
        final String amqUser = ConsumerRunner.getEnvOrDefault("AMQ_USER", "user");
        final String amqPassword = ConsumerRunner.getEnvOrDefault("AMQ_PASSWORD", "password");
        final Integer producerThreadCount = Integer.parseInt(ConsumerRunner.getEnvOrDefault("CONSUMER_THREAD", "10"));
        final String[] queueNames = ConsumerRunner.getEnvOrDefault("QUEUE_NAMES", "GROUPING_QUEUE,NOT_GROUPING_QUEUE").split(",");

        for(int count = 0; count < producerThreadCount; count++){
            Consumer producer = new Consumer(count, amqUser, amqPassword, amqPassword, queueNames[count%queueNames.length]);
            producer.start();
        }

    }

    private static String getEnvOrDefault(String envName, String defaultValue) {
        return System.getenv(envName) != null ? System.getenv(envName) : defaultValue;
    }

}
