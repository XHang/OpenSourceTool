package com.cxh.topics;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 主题交换
 * 其实就是允许消息带有多个routingKey
 * 从而发送到特定的几类队列中
 * 演示步骤
 * 1. 运行 此包的Receiver 类
 * 2. 输入bindingKey,先输入com.*吧
 * 3. 运行此类
 * 4. 输入 com.cxh
 * 5  输入  this is cxh
 * 6. 观察现象
 * 预期现象：
 * Receiver 类运行控制台输出响应 this is cxh
 * 证明模糊匹配生效
 */
public class Sender {
    private static final String EXCHANGE_NAME = "topic_logs";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            channel.exchangeDeclare(EXCHANGE_NAME, "topic");
            String routingKey = getRoutingKey();
            System.out.println("please input message");
            String message =  getInput();
            //routingKey有要求的哦，其实是多个routingKey，只不过中间需要用.隔开
            channel.basicPublish(EXCHANGE_NAME, routingKey, null, message.getBytes("UTF-8"));
            System.out.println(" [x] Sent '" + routingKey + "':'" + message + "'");
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
    private static  String getRoutingKey(){
        System.out.println("please input  rountingKey,To exit type exit");
        System.out.println("For Example <abc.cbf> ");
        String rountingKey = getInput();
        return rountingKey;
    }
}
