package com.cxh.rpc;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * RPC客户端，发送一个数字，到服务器用斐波纳契计算出值，并返回
 */
public class Client {

    public static void main(String[] argv) throws TimeoutException {
        try (RPCClient fibonacciRpc = new RPCClient()) {
            for (int i = 0; i < 32; i++) {
                String i_str = Integer.toString(i);
                System.out.println(" [x] Requesting fib(" + i_str + ")");
                String response = fibonacciRpc.call(i_str);
                System.out.println(" [.] Got '" + response + "'");
            }
        } catch (IOException | TimeoutException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
