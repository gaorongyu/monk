package com.fngry.monk.biz.demo.rpc.provider;

import com.fngry.monk.biz.demo.rpc.RpcFramework;
import com.fngry.monk.biz.demo.rpc.provider.service.HelloService;
import com.fngry.monk.biz.demo.rpc.provider.service.HelloServiceImpl;

/**
 *
 * 服务提供者
 *
 * Created by gaorongyu on 17/1/19.
 */
public class RpcProvider {

    public static void main(String[] args) throws Exception {
        HelloService service = new HelloServiceImpl();
        RpcFramework.export(service, RpcFramework.SERVER_PORT);
    }

}
