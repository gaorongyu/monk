package com.fngry.monk.biz.demo.io.netty.server;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

/**
 * 服务端消息处理器
 * Created by gaorongyu on 17/2/6.
 */
public class EchoServerHandler extends SimpleChannelHandler {

    public void messageReceived(
            ChannelHandlerContext ctx, MessageEvent e) throws Exception {
        Channel channel = e.getChannel();

        ChannelBuffer inBuffer = (ChannelBuffer) e.getMessage();
        String msg = new String(inBuffer.array());

        String returnMsg = " server return msg : " + msg;
        ChannelBuffer outBuffer = ChannelBuffers.copiedBuffer(returnMsg.getBytes());

        System.out.println(" server receive message is " + msg);
        channel.write(outBuffer);
    }

}
