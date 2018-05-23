package com.feizi.rpc.provider.server;

/**
 * NettyServer服务端启动类
 * Created by feizi on 2018/5/4.
 */
public class ServerBootstrap {
    public static void main(String[] args) {
        /**
         * 服务端实现逻辑：
         * 1、创建一个Netty服务端server
         * 2、实现一个自定义的handler，用于处理判断是否符合通信的约定（协议）
         * 3、如果符合约定，就创建一个接口的实现类
         * 4、调用方法返回处理结果
         */
        NettyServer.startServer("localhost", 9999);
    }
}
