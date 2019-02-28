package com.cxh.rpc;

import com.rabbitmq.client.*;

public class Server {

    private static final String RPC_QUEUE_NAME = "rpc_queue";

    private final static String SERVER_ADDRESS = "www.thisisweb.tk";

    /**
     * 斐波纳契程序
     * @param n
     * @return
     */
    private static int fib(int n) {
        if (n == 0) return 0;
        if (n == 1) return 1;
        return fib(n - 1) + fib(n - 2);
    }

    public static void main(String[] argv) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");

        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel()) {
            //声明一个用于接受请求的队列
            channel.queueDeclare(RPC_QUEUE_NAME, false, false, false, null);
            //清除此队列的内容
            channel.queuePurge(RPC_QUEUE_NAME);
            //只给该应用分配一个任务
            channel.basicQos(1);

            System.out.println(" [x] Awaiting RPC requests");

            Object monitor = new Object();
            //从队列中接受到消息了
            DeliverCallback deliverCallback = (consumerTag, delivery) -> {
                System.out.println("接受到客户端数据了");
                //设置服务端返回给客户端的消息属性
                System.out.println("CorrelationId is "+delivery.getProperties().getCorrelationId());
                AMQP.BasicProperties replyProps = new AMQP.BasicProperties
                        .Builder()
                        .correlationId(delivery.getProperties().getCorrelationId())
                        .build();

                String response = "";

                try {
                    String message = new String(delivery.getBody(), "UTF-8");
                    System.out.println("message is "+message);
                    int n = Integer.parseInt(message);
                    System.out.println("message is "+delivery.getProperties().getCorrelationId());
                    System.out.println(" [.] fib(" + message + ")");
                    response += fib(n);
                } catch (RuntimeException e) {
                    System.out.println(" [.] " + e.toString());
                } finally {
                    System.out.println("准备返回到队列"+delivery.getProperties().getReplyTo()+"了");
                    //将斐波切纳的结果返回给客户端，第二个参数其实是客户端那边提前设置进去的回调队列名
                    channel.basicPublish("", delivery.getProperties().getReplyTo(), replyProps, response.getBytes("UTF-8"));
                    System.out.println("返回结束，准备应答");
                    channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
                    // RabbitMq consumer worker thread notifies the RPC server owner thread
                    synchronized (monitor) {
                        monitor.notify();
                    }
                }
            };

            channel.basicConsume(RPC_QUEUE_NAME, false, deliverCallback, (consumerTag -> { }));
            // Wait and be prepared to consume the message from RPC client.
            while (true) {
                synchronized (monitor) {
                    try {
                        monitor.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

}
