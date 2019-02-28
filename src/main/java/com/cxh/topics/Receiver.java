package com.cxh.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Receiver {
    private static final String EXCHANGE_NAME = "topic_logs";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.exchangeDeclare(EXCHANGE_NAME, "topic");
        String queueName = channel.queueDeclare().getQueue();

        for (;;) {
            System.out.println("please input bindKey. support wildcards * and #");
            System.out.println("* is can substitute for exactly one word.");
            System.out.println("#  can substitute for zero or more words.");
            System.out.println("enter 'exit' to exit");
            String bindingKey = getInput();
            if ("exit".equals(bindingKey)){
                break;
            }
            channel.queueBind(queueName, EXCHANGE_NAME, bindingKey);
        }

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" +
                    delivery.getEnvelope().getRoutingKey() + "':'" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
    private static  String getInput(){
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            return  reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException((e));
        }
    }
}
