package org.ippul.amq.example;

public class ProducerRunner {

    public static void main(String[] args) throws Exception {
        final String amqUser = ProducerRunner.getEnvOrDefault("AMQ_USER", "user");
        final String amqPassword = ProducerRunner.getEnvOrDefault("AMQ_PASSWORD", "password");
        final Integer producerThreadCount = Integer.parseInt(ProducerRunner.getEnvOrDefault("PRODUCER_THREAD", "10"));
        final String[] queueNames = ProducerRunner.getEnvOrDefault("QUEUE_NAMES", "GROUPING_QUEUE,NOT_GROUPING_QUEUE").split(",");

        for(int count = 0; count < producerThreadCount; count++){
            Producer producer = new Producer(count, amqUser, amqPassword, amqPassword, queueNames[count%queueNames.length]);
            producer.start();
        }

    }

    private static String getEnvOrDefault(String envName, String defaultValue) {
        return System.getenv(envName) != null ? System.getenv(envName) : defaultValue;
    }
}
