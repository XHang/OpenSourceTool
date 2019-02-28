package com.cxh.rpc;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

public class RPCClient implements AutoCloseable{

    private Connection connection;
    private Channel channel;
    private String requestQueueName = "rpc_queue";
    private String callQueueName = "callQueueName";

    private final static String SERVER_ADDRESS = "www.thisisweb.tk";

    public RPCClient() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");

        connection = factory.newConnection();
        channel = connection.createChannel();
        //每个客户端只需要一个队列即可，用correlationId区分每个请求
        channel.queueDeclare(callQueueName, false, false, false, null);
    }


    public String call(String message) throws IOException, InterruptedException {

        final String corrId = UUID.randomUUID().toString();
        //声明一个队列  TODO 但是应该没必要了吧，队列完全可以每个RPC只用一个
        System.out.println("corrId is "+corrId);
        System.out.println("callQueueName is "+callQueueName);
        AMQP.BasicProperties props = new AMQP.BasicProperties.Builder()
                //弄一个corrId，用于标识每次RPC请求
                .correlationId(corrId)
                //设置回调的队列
                .replyTo(callQueueName)
                //构建消息的队列
                .build();
        //推送消息到队列中
        System.out.println("准备发请求到"+requestQueueName+"队列中");
        channel.basicPublish("", requestQueueName, props, message.getBytes("UTF-8"));
        System.out.println("发送OK");

        //该类可以堵塞请求，直至响应到达，容量为1，则一个响应到达就可以解除堵塞
        final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);
        //设置回调程序
        String ctag = channel.basicConsume(callQueueName, true, (consumerTag, delivery) -> {
            //如果是这个RPC客户端发出的请求，则corrId应一样，不一样则忽略消息
            String callCorrId = delivery.getProperties().getCorrelationId();
            System.out.println("取到服务器的反馈了，反馈送来的corrId是"+callCorrId);
            if (callCorrId.equals(corrId)) {
                String responseMsg = new String(delivery.getBody(), "UTF-8");
                System.out.println("已经匹配啦，服务器的反馈是"+responseMsg);
                response.offer(responseMsg);
            }
        }, consumerTag -> {
        });
        //获取回调数据
        System.out.println("准备拿数据了");
        String result = response.take();
        System.out.println("数据拿到了");
        //取消消费者
        channel.basicCancel(ctag);
        return result;
    }

    @Override
    public void close() throws IOException {
        connection.close();

    }

}
