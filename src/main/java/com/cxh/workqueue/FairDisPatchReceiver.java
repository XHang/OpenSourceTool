package com.cxh.workqueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

/**
 * 接受方，可以打开多个接受方，可以看到接受方在循环获取消息,并且公平派遣任务
 * 演示步骤：
 * 1.首先运行CycleSender
 * 2. 运行两个该类的主方法
 * 3. 在CycleSender运行的分别填入
 *      这.是.一.个.耗.时.很.长.的.消.息.啦.........................................
 *      哦，酱紫，我造了.
 * 4. 观察现象，你应该可以看到两个消息依次出现在该类的运行主窗口上，但是第二个消息执行的比较快。马上就应答了
 * 5. 再往CycleSender运行的窗口填入
 *      这个一个新任务，找个悠哉悠哉的家伙去做
 * 6. 观察现象，按照以往的做法，RabbitMq会遵循循环调度，把任务分配给第一个窗口。
 * 但是这次我们设置了公平派遣，由于第一次窗口的任务还没执行完
 * 所以理论上，任务被再次分配给第二个窗口
 *
 *
 */
public class FairDisPatchReceiver {
    private final static String QUEUE_NAME = "hello";
    private final static String SERVER_ADDRESS = "www.thisisweb.tk";
    public static void main(String [] args) throws Exception{
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(SERVER_ADDRESS);
        factory.setUsername("guest");
        factory.setPassword("guest");
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        //如果接受方目前有一个任务正在执行中，则暂时不向它继续发送消息
        channel.basicQos(1);
        //可能接受方会在发送方启动前先启动，所以会先声明一个队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //回调消息，把消息处理
        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            String message = new String(delivery.getBody(), "UTF-8");
            try {
                //模拟实际运行的任务
                System.out.println("已收到数据："+message);
                for (char ch: message.toCharArray()) {
                    if (ch == '.') Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }finally {
                System.out.println("处理完毕，准备应答");
                channel.basicAck(delivery.getEnvelope().getDeliveryTag(),false);
                System.out.println("处理完毕，应答完毕");
            }
        };
        channel.basicConsume(QUEUE_NAME, false, deliverCallback, consumerTag -> { });
    }
    private static void doWork(String task){

    }
}

