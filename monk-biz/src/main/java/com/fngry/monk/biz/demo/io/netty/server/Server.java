package com.fngry.monk.biz.demo.io.netty.server;

import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelFactory;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.ChannelPipelineFactory;
import org.jboss.netty.channel.Channels;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

import java.net.InetSocketAddress;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Netty Server
 * Created by gaorongyu on 17/2/6.
 */
public class Server {

    public void start() {

        ExecutorService boss = Executors.newCachedThreadPool();
        ExecutorService worker = Executors.newCachedThreadPool();
        ChannelFactory channelFactory = new NioServerSocketChannelFactory(boss, worker);

        ServerBootstrap serverBootstrap = new ServerBootstrap(channelFactory);
        serverBootstrap.setPipelineFactory(new ChannelPipelineFactory() {
            @Override
            public ChannelPipeline getPipeline() throws Exception {
                return Channels.pipeline(new EchoServerHandler());
            }
        });

        serverBootstrap.bind(getServerAddress());
    }

    public InetSocketAddress getServerAddress() {
        return new InetSocketAddress(1235);
    }

    public static void main(String[] args) throws Exception {
        Server server = new Server();
        server.start();
    }

}
