package com.cxh.helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Sender  {
    /**
     * 队列名
     */
    private final static String QUEUE_NAME = "hello";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";
    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        //需要设置用户名和密码，除非你的RabbitMq设置无用户名和密码远程登录
        factory.setUsername("guest");
        factory.setPassword("guest");
        try (Connection connection = factory.newConnection();Channel channel = connection.createChannel()) {
            /**
             * 声明一个队列，当队列名不存在时创建
             */
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            String message = "你个吊毛!";
            /**
             * 发送消息
             */
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
            System.out.println(" [x] Sent '" + message + "'");
        }
    }

}
