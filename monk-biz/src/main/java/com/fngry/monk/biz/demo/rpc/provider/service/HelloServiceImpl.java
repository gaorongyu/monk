package com.fngry.monk.biz.demo.rpc.provider.service;

/**
 * Created by gaorongyu on 17/1/19.
 */
public class HelloServiceImpl implements HelloService {

    @Override
    public String hello(String name) {
        String result = " name is: " + name;
        System.out.println(result);
        return result;
    }

}
