package com.fngry.monk.biz.demo.rpc.consumer;

import com.fngry.monk.biz.demo.rpc.RpcFramework;
import com.fngry.monk.biz.demo.rpc.provider.service.HelloService;

/**
 *
 * 服务消费者
 *
 * Created by gaorongyu on 17/1/19.
 */
public class RpcConsumer {

    public static void main(String[] args) throws Exception {
        HelloService service = RpcFramework.refer(
                HelloService.class, RpcFramework.SERVER_IP, RpcFramework.SERVER_PORT);

        for (int i = 0; i < 100; i++) {
            String result = service.hello(" hello " + i);
            System.out.println(" consumer get result: " + result);
            Thread.sleep(1000);
        }
    }

}
