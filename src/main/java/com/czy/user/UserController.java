package com.czy.user;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.Controller;
import com.czy.core.annotation.GetMapping;
import com.czy.core.annotation.PostMapping;
import com.czy.user.service.IUserService;
import com.czy.util.model.ResultVO;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author chenzy
 * @description
 * @since 2020-04-23
 */
@Controller("/user")
public class UserController {
    @Auto
    private IUserService userService;

    @GetMapping("/login")
    public Object login(ObjectNode dataJson){
        System.out.println(dataJson);
        return "登录成功";
    }
    @PostMapping
    public ResultVO creatUser(User user){
        userService.insert(user);
        return new ResultVO(user);
    }
}
