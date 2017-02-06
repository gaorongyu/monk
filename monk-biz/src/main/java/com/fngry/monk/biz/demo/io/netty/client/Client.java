package com.fngry.monk.biz.demo.io.netty.client;

import org.jboss.netty.bootstrap.ClientBootstrap;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.*;
import org.jboss.netty.channel.socket.nio.NioClientSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty Client
 * Created by gaorongyu on 17/2/6.
 */
public class Client {

    public void start() {
        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        ChannelFactory channelFactory = new NioClientSocketChannelFactory(boss, worker);

        ClientBootstrap clientBootstrap = new ClientBootstrap(channelFactory);
        clientBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new EchoClientHandler());
            }
        });

        ChannelFuture channelFuture = clientBootstrap.connect(getServerAddress());

        Channel channel = channelFuture.getChannel();

        send(channel);
    }

    /**
     *
     * 发送消息
     *
     * @param channel
     */
    private void send(Channel channel) {
        ChannelBuffer buffer = ChannelBuffers.copiedBuffer("hello!".getBytes());
        channel.write(buffer);
    }

    public InetSocketAddress getServerAddress() {
        return new InetSocketAddress("localhost", 1235);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client();
        client.start();
    }

}
