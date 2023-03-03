package org.ippul.amq.example;

public class ConsumerRunner {
    
    public static void main(String[] args) throws Exception {
        final String amqUser = ConsumerRunner.getEnvOrDefault("AMQ_USER", "user");
        final String amqPassword = ConsumerRunner.getEnvOrDefault("AMQ_PASSWORD", "password");
        final Integer producerThreadCount = Integer.parseInt(ConsumerRunner.getEnvOrDefault("CONSUMER_THREAD", "10"));
        final String[] queueNames = ConsumerRunner.getEnvOrDefault("QUEUE_NAMES", "GROUPING_QUEUE,NOT_GROUPING_QUEUE").split(",");
        System.out.println("user:" + amqUser);
        for(int count = 0; count < producerThreadCount; count++){
            Consumer consumer = new Consumer(count, "tcp://amq-broker-example-hdls-svc:61616", amqUser, amqPassword, queueNames[count%queueNames.length]);
            consumer.start();
        }

    }

    private static String getEnvOrDefault(String envName, String defaultValue) {
        System.out.println(envName + ": " + System.getenv(envName) != null ? System.getenv(envName) : defaultValue);
        return System.getenv(envName) != null ? System.getenv(envName) : defaultValue;
    }

}
