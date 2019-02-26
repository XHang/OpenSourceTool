package com.cxh.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 消息订阅，发布搞基版
 * 可以指定订阅方
 */
public class Receiver {

    private static final String EXCHANGE_NAME = "direct_logs";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";


    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //直联的`exchange`类型
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");
        //创建一个非持久化，用完即删，独占性的队列
        String queueName = channel.queueDeclare().getQueue();


        for (;;) {
            System.out.println("请输入您要绑定到exchange的key，输入exit退出");
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
