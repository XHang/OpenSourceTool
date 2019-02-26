package com.cxh.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 消息订阅-发布模型的发送端
 * 1. 需要声明一个广播类型的交换器
 * 2. 发送消息
 */
public class Sender {

    private static final String EXCHANGE_NAME = "logs";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";


    public static void main(String [] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //声明一个交换机制类型为fanout的交换机制
            channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
            System.out.println("please input msg");
            String message = getInput();
            //使用此交换机制，发送消息
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
            System.out.println(" [x] already  Sent '" + message + "'");
        }
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
