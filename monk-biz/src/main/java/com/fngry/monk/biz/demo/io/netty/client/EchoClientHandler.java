package com.fngry.monk.biz.demo.io.netty.client;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 客户端消息处理器
 * Created by gaorongyu on 17/2/6.
 */
public class EchoClientHandler extends SimpleChannelHandler {

    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel channel = e.getChannel();

        ChannelBuffer channelBuffer = (ChannelBuffer) e.getMessage();
        String msg = new String(channelBuffer.array());
        System.out.println(" cleint e.getMessage: " + msg);
    }

}
