package com.cxh.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 接受方，可以打开多个接受方，可以看到接受方在循环获取消息
 */
public class CycleReceiver {
    private final static String QUEUE_NAME = "hello";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";
    public static void main(String [] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //可能接受方会在发送方启动前先启动，所以会先声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //回调消息，把消息处理
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            doWork(message);
        };
        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> { });
    }
    private static void doWork(String task){
        try {
            //模拟实际运行的任务
            System.out.println("已收到数据："+task);
            for (char ch: task.toCharArray()) {
                if (ch == '.') Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

