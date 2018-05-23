package com.feizi.rpc.provider.server;

import com.feizi.rpc.provider.handler.HelloServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Netty服务端
 * Created by feizi on 2018/5/4.
 */
public class NettyServer {
    public static void startServer(String hostName, final int port){
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();

            bootstrap.group(eventLoopGroup)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pipeline = socketChannel.pipeline();
                            pipeline.addLast(new StringDecoder());
                            pipeline.addLast(new StringEncoder());
                            pipeline.addLast(new HelloServerHandler());
                        }
                    });
            bootstrap.bind(hostName, port).sync();
            System.out.println("Netty Server start SUCCESS....");
        } catch (InterruptedException e) {
            e.printStackTrace();
            System.out.println("Netty Server start failure...");
        }
    }
}
