package com.cxh.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

/**
 * 工作队列的发送方，将可以从输入框接受数据，不断地发送消息给队列
 */
public class Sender {

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
        try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
            while(true){
                System.out.println("please Input Msg");
                String message  = getInput();
                /**
                 * 声明一个队列，当队列名不存在时创建
                 */
                channel.queueDeclare(QUEUE_NAME, false, false, false, null);
                /**
                 * 发送消息
                 */
                channel.basicPublish("", QUEUE_NAME, null, message.getBytes());
                System.out.println(" [x] Sent '" + message + "'");
            }
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
