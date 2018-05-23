package com.feizi.rpc.provider.handler;

import com.feizi.rpc.consumer.ClientBootstrap;
import com.feizi.rpc.provider.impl.HelloServiceImpl;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * 用于处理请求数据
 * Created by feizi on 2018/5/4.
 */
public class HelloServerHandler extends ChannelInboundHandlerAdapter{

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(null == msg){
            System.out.println("参数为空...");
            return;
        }

        //如果符合约定，则调用本地方法，返回数据
        if(msg.toString().startsWith(ClientBootstrap.providerName)){
            String result = new HelloServiceImpl()
                    .hello(msg.toString().substring(msg.toString().lastIndexOf("#") + 1));
            ctx.writeAndFlush(result);
        }
    }
}
