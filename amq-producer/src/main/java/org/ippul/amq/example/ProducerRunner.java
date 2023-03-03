package org.ippul.amq.example;

public class ProducerRunner {

    public static void main(String[] args) throws Exception {
        final String amqUser = ProducerRunner.getEnvOrDefault("AMQ_USER", "user");
        final String amqPassword = ProducerRunner.getEnvOrDefault("AMQ_PASSWORD", "password");
        final Integer producerThreadCount = Integer.parseInt(ProducerRunner.getEnvOrDefault("PRODUCER_THREAD", "10"));
        final String[] queueNames = ProducerRunner.getEnvOrDefault("QUEUE_NAMES", "GROUPING_QUEUE,NOT_GROUPING_QUEUE").split(",");

        for(int count = 0; count < producerThreadCount; count++){
            Producer producer = new Producer(count, "tcp://amq-broker-example-hdls-svc:61616", amqUser, amqPassword, queueNames[count%queueNames.length]);
            producer.start();
        }

    }

    private static String getEnvOrDefault(String envName, String defaultValue) {
        System.out.println(envName + ": " + System.getenv(envName) != null ? System.getenv(envName) : defaultValue);
        return System.getenv(envName) != null ? System.getenv(envName) : defaultValue;
    }
}
