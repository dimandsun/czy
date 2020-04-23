package com.czy.core.test.controller;


import com.czy.core.CoreContainer;
import com.czy.core.annotation.Auto;
import com.czy.core.annotation.Controller;
import com.czy.core.annotation.GetMapping;
import com.czy.core.test.service.ITestService$;

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
    @GetMapping("/initeContainer")
    public Object initeContainer(){
        CoreContainer.getInstance().reLoadContainer();
        return null;
    }
}
