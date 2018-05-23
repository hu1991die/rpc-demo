package com.feizi.rpc.consumer;

import com.feizi.rpc.consumer.handler.HelloClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.smtp.SmtpRequestEncoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.lang.reflect.Proxy;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * RPC消费者
 * Created by feizi on 2018/5/4.
 */
public class RpcConsumer {
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    private static HelloClientHandler client;

    /**
     * 创建一个代理对象
     * @param serviceClass service类
     * @param providerName 提供者
     * @return
     */
    public Object createProxy(final Class<?> serviceClass, final String providerName){
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                new Class<?>[]{serviceClass}, (proxy, method, args) -> {
                    if(null == client){
                        initClient();
                    }

                    //设置参数
                    client.setParam(providerName + args[0]);
                    return executorService.submit(client).get();
                });
    }

    private static void initClient(){
        client = new HelloClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        ChannelPipeline pipeline = socketChannel.pipeline();
                        pipeline.addLast(new StringDecoder());
                        pipeline.addLast(new StringEncoder());
                        pipeline.addLast(client);
                    }
                });
        try {
            bootstrap.connect("localhost", 9999).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
