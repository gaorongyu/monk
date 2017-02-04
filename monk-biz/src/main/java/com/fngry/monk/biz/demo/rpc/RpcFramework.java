package com.fngry.monk.biz.demo.rpc;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.net.ServerSocket;
import java.net.Socket;

/**
 *
 * RPC框架
 *
 * Created by gaorongyu on 17/1/19.
 */
public class RpcFramework {

    public static final String SERVER_IP = "127.0.0.1";

    public static final int SERVER_PORT = 1234;

    /**
     *
     * 暴露服务
     *
     * @param service 服务
     * @param port 服务端口
     */
    public static void export(final Object service, int port) throws Exception {

        ServerSocket server = new ServerSocket(port);

        for ( ; ; ) {
            final Socket socket = server.accept();
            new Thread(new Runnable() {
                @Override
                public void run() {
                    doExport(socket, service);
                }
            }).start();
        }
    }

    /**
     *
     * 引用服务
     *
     * @param interfaceClass 接口类型
     * @param host 服务器ip
     * @param port 服务器端口
     * @param <T>
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T> T refer(final Class<T> interfaceClass,
            final String host, final int port) {

        return (T) Proxy.newProxyInstance(
                interfaceClass.getClassLoader(),
                new Class[] {interfaceClass},
                new InvocationHandler() {
                    @Override
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                        return doRefer(proxy, method, args, host, port);
                    }
                });
    }

    /**
     *
     * 暴露服务
     *
     * @param socket
     * @param service
     */
    public static void doExport(Socket socket, final Object service)  {
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        try {
            oos = new ObjectOutputStream(socket.getOutputStream());
            ois = new ObjectInputStream(socket.getInputStream());

            String methodName = ois.readUTF();
            Class<?>[] parameterTypes = (Class<?>[]) ois.readObject();
            Object[] args = (Object[]) ois.readObject();

            Method method = service.getClass().getMethod(methodName, parameterTypes);
            Object result = method.invoke(service, args);

            oos.writeObject(result);

        } catch (Exception e) {
            try {
                oos.writeObject(e);
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        } finally {
            try {
                if (oos != null) {
                    oos.close();
                }
                if (ois != null) {
                    ois.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     *
     * 引用服务
     *
     * @param proxy
     * @param method
     * @param args
     * @param host
     * @param port
     * @return
     * @throws Throwable
     */
    public static Object doRefer(Object proxy, Method method, Object[] args,
            String host, int port) throws Throwable {
        Socket socket = new Socket(host, port);
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;

        try {
            oos = new ObjectOutputStream(socket.getOutputStream());

            oos.writeUTF(method.getName());
            oos.writeObject(method.getParameterTypes());
            oos.writeObject(args);

            ois = new ObjectInputStream(socket.getInputStream());

            Object result = ois.readObject();
            if (result instanceof Throwable) {
                throw (Throwable) result;
            }
            return result;

        } finally {
            try {
                if (ois != null) {
                    ois.close();
                }
                if (oos != null) {
                    oos.close();
                }
                if (socket != null) {
                    socket.close();
                }
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }

}
