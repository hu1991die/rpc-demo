package com.feizi.rpc.consumer.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

/**
 * 客户端处理类
 * Created by feizi on 2018/5/4.
 */
public class HelloClientHandler extends ChannelInboundHandlerAdapter implements Callable {
    private ChannelHandlerContext context;
    private String result;
    private String param;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.context = ctx;
    }

    /**
     * 客户端收到服务端请求数据，唤醒等待线程
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    public synchronized void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(null == msg){
            return;
        }
        result = msg.toString();
        notify();
    }

    /**
     * 写数据，开始等待唤醒
     * @return
     * @throws Exception
     */
    @Override
    public synchronized Object call() throws Exception {
        context.writeAndFlush(param);
        wait();
        return result;
    }

    public void setParam(String param) {
        this.param = param;
    }
}
