package com.czy.swing.user;

import com.czy.core.annotation.Controller;
import com.czy.core.annotation.GetMapping;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
@Controller
public class UserController {

    @GetMapping("/login")
    public Object login(ObjectNode dataJson){
        System.out.println(dataJson);
        return "登录成功";
    }
}
