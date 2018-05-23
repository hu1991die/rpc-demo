package com.feizi.rpc.provider.impl;

import com.feizi.rpc.intf.HelloService;

/**
 * 接口实现类
 * Created by feizi on 2018/5/4.
 */
public class HelloServiceImpl implements HelloService {
    @Override
    public String hello(String msg) {
        return msg != null ? msg + "-----------------> I am fine." : "I am fine.";
    }
}
