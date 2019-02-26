package com.cxh.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 接受方，可以打开多个接受方，可以看到接受方在循环获取消息
 * 演示消息确认的作用
 * 1. 首先打开CycleSender，运行
 * 2. 运行多个本类实例
 * 3. 往CycleSender输入一个长长的消息后用.结尾
 * 4. 查看哪个消费者接受到这个信息了，在该消费者应答之前，强行关闭该线程
 * 5. 查看该消息有没有被转发到其他消费者
 * 实验结果，
 *没有应答的消息将被转发到其他消费者
 */
public class DurabilityReceiver {
    private final static String QUEUE_NAME = "durability_hello";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";
    public static void main(String [] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //可能接受方会在发送方启动前先启动，所以会先声明一个队列
        channel.queueDeclare(QUEUE_NAME, true, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //回调消息，把消息处理
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String task = new String(delivery.getBody(), "UTF-8");
            try {
                //模拟实际运行的任务
                System.out.println("已收到数据："+task);
                for (char ch: task.toCharArray()) {
                    if (ch == '.') Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("已处理完数据，准备应答");
                //手动应答不可缺少次步骤。否则后果是致命的
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                System.out.println("应答完毕");
            }
        };
        //autoAck表示不要自动应答，自己手动应答
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }
}

