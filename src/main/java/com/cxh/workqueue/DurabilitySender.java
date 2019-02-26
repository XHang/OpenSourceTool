package com.cxh.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 工作队列的发送方，将可以从输入框接受数据，不断地发送消息给队列
 * 该发送方的特性是，发送的消息将被持久化存储
 * 这个发送方可以将消息持久化保存。
 * 演示步骤：
 * 1. 运行本类的main方法
 * 2. 发送几个消息后
 * 3.关闭本类
 * 4. 杀死RabbitMq的服务器线程，或者正常终止
 * 5. 使RabbitMq复活
 * 6. 开启任意一个消费者（只要所用的队列和该类的一致，可以用DurabilityReceiver类），接受数据
 * 7. 消费者是否能收到数据？
 * 实验结果：
 * 可以收到，数据有持久化存到硬盘中
 */
public class DurabilitySender {

    /**
     * 队列名
     */
    private final static String QUEUE_NAME = "durability_hello";
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
                 * 声明一个持久化队列，当队列名不存在时创建
                 */
                channel.queueDeclare(QUEUE_NAME, true, false, false, null);
                /**
                 * 发送消息,并且声明消息持久化的格式是TEXT_PLAIN
                 */
                channel.basicPublish("", QUEUE_NAME,  MessageProperties.PERSISTENT_TEXT_PLAIN, message.getBytes());
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
