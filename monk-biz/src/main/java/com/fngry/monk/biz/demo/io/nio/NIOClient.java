package com.fngry.monk.biz.demo.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * Created by gaorongyu on 17/2/6.
 */
public class NIOClient {

    private Selector selector;

    public void init() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        socketChannel.configureBlocking(false);

        this.selector = Selector.open();

        socketChannel.connect(getServerAddress());

        socketChannel.register(selector, SelectionKey.OP_CONNECT);
    }

    public void listen() throws IOException {
        System.out.println(" client start listen! ");

        while (true) {
            selector.select();

            Iterator iterator = this.selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = (SelectionKey) iterator.next();
                iterator.remove();

                if (key.isConnectable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    if (channel.isConnectionPending()) {
                        channel.finishConnect();
                    }
                    channel.configureBlocking(false);

                    channel.write(ByteBuffer.wrap(
                            new String(" send to server message").getBytes()));

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
        System.out.println(" client receive message is : " + msg);

        ByteBuffer outBuffer = ByteBuffer.wrap(msg.getBytes());
        channel.write(outBuffer);
    }

    public InetSocketAddress getServerAddress() {
        return new InetSocketAddress("localhost", 1233);
    }

    public static void main(String[] args) throws Exception {
        NIOClient nioClient = new NIOClient();
        nioClient.init();
        nioClient.listen();
    }

}
