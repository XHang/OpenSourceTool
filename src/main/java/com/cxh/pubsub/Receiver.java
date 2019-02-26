package com.cxh.pubsub;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 消息订阅-发布模型中的接受端
 */
public class Receiver {


    private static final String EXCHANGE_NAME = "logs";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";
    public static void main(String [] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //声明一个exchange，命名为EXCHANGE_NAME
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //随机获取一个队列名
        String queueName = channel.queueDeclare().getQueue();
        //将队列名和exchange绑定起来，一旦exchange接受到数据，就广播到该队列
        channel.queueBind(queueName, EXCHANGE_NAME, "");

        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            System.out.println(" [x] Received '" + message + "'");
        };
        channel.basicConsume(queueName, true, deliverCallback, consumerTag -> { });
    }
}
