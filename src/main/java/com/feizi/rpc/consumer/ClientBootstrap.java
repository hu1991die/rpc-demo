package com.feizi.rpc.consumer;

import com.feizi.rpc.intf.HelloService;

/**
 * Created by feizi on 2018/5/4.
 */
public class ClientBootstrap {
    public static final String providerName = "HelloService#hello#";

    public static void main(String[] args) {
        RpcConsumer rpcConsumer = new RpcConsumer();
        //创建一个代理对象
        HelloService helloService = (HelloService) rpcConsumer.createProxy(HelloService.class, providerName);
        for (; ;){
            try {
                Thread.sleep(1000);
                System.out.println(helloService.hello("Are you ready?"));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
