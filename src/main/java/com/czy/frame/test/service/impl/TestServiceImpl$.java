package com.czy.frame.test.service.impl;

import com.czy.frame.core.annotation.Auto;
import com.czy.frame.core.annotation.Service;
import com.czy.frame.test.dao.ITestDao;
import com.czy.frame.test.service.ITestService$;
import com.czy.frame.test.service.ITestService2$;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;

/**
 * @author chenzy
 * @description
 * @since 2020-03-31
 */
@Service
public class TestServiceImpl$ implements ITestService$ {
    Logger logger = LoggerFactory.getLogger(TestServiceImpl$.class);
    @Auto
    private ITestService2$ testService2$;
    @Auto
    private ITestDao testDao;

    @Override
    public Object test() {
        Map reult=testDao.get(null);
        logger.info("测试日志");
        return "测试接口1：成功：";
    }
    @Override
    public String test2() {
        return "测试接口1："+testService2$.test();
    }
}
