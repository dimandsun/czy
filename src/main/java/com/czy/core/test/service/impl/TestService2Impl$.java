package com.czy.core.test.service.impl;


import com.czy.core.annotation.Service;
import com.czy.core.test.service.ITestService2$;

/**
 * @author chenzy
 * @description
 * @since 2020-03-31
 */
@Service
public class TestService2Impl$ implements ITestService2$ {
    @Override
    public String test() {
        return "测试接口2：成功";
    }
}
