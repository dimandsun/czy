package com.czy.user.control;

import com.czy.core.annotation.*;
import com.czy.user.service.IUserService;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
@Controller("/login")
public class LoginController {
    @Auto
    private IUserService userService;

    @GetMapping
    public Object login(ObjectNode dataJson){
        System.out.println(dataJson);
        return "登录成功";
    }
}
