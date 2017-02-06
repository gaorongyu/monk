package com.fngry.monk.biz.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by gaorongyu on 17/2/6.
 */
public class NIOServer {

    private Selector selector;

    public void init() throws IOException {
        ServerSocketChannel socketChannel = ServerSocketChannel.open();

        socketChannel.configureBlocking(false);
        socketChannel.socket().bind(getServerAddress());

        this.selector = Selector.open();

        socketChannel.register(selector, SelectionKey.OP_ACCEPT);
    }

    public void listen() throws IOException {
        System.out.println(" server start listen! ");

        while (true) {
            selector.select();

            Iterator iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();

                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel channel = server.accept();
                    channel.configureBlocking(false);

                    channel.write(ByteBuffer.wrap(
                            new String(" send to client message").getBytes()));

                    channel.register(this.selector, SelectionKey.OP_READ);
                } else if (key.isReadable()) {
                    read(key);
                }
            }
        }
    }

    public void read(SelectionKey key) throws IOException {
        SocketChannel channel = (SocketChannel) key.channel();

        ByteBuffer buffer = ByteBuffer.allocate(100);
        channel.read(buffer);
        byte[] data = buffer.array();
        String msg = new String(data);
        System.out.println(" server receive message is : " + msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);
    }

    public InetSocketAddress getServerAddress() {
        return new InetSocketAddress(1233);
    }

    public static void main(String[] args) throws Exception {
        NIOServer nioServer = new NIOServer();
        nioServer.init();
        nioServer.listen();
    }


}
