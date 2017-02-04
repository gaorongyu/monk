package com.fngry.monk.server;

import com.fngry.monk.client.DemoService;

/**
 * Created by gaorongyu on 17/2/4.
 */
public class DemoServiceImpl implements DemoService {

    @Override
    public String hello(String name) {
        String result = " hello " + name + " this is monk! ";
        System.out.println(result);

        return result;
    }

}
