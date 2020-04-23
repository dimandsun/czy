package com.czy.frame.test.controller;

import com.czy.frame.core.CoreContainer;
import com.czy.frame.core.annotation.*;
import com.czy.frame.test.service.ITestService$;

/**
 * @author chenzy
 * @description
 * @since 2020-03-30
 */
@Controller
public class TestController$ {
    @Auto
    private ITestService$ testService$;

    @GetMapping("/$test/1")
    public Object test(){
        return testService$.test();
    }
    @GetMapping("/initeContainer$")
    public Object initeContainer(){
        CoreContainer.getInstance().reLoadContainer();
        return null;
    }
}
