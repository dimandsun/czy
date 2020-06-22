package com.czy.user.control;

import com.czy.core.annotation.*;
import com.czy.core.annotation.bean.Controller;
import com.czy.core.annotation.mapping.*;
import com.czy.user.model.User;
import com.czy.user.service.IUserService;
import com.czy.util.model.ResultVO;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author chenzy
 *
 * @since 2020-04-23
 */
@Controller
@Mapping("/user")
public class UserController {
    @Auto
    private IUserService userService;

    @GetMapping("/login")
    public Object login(ObjectNode dataJson){
        System.out.println(dataJson);
        return "登录成功";
    }
    @PostMapping
    public ResultVO insert(User user){
        return userService.insert(user);
    }
    @DeleteMapping
    public ResultVO delete(User user){
        return userService.delete(user);
    }
    @PutMapping
    public ResultVO update(User user){
        return userService.update(user);
    }
    @GetMapping
    public ResultVO get(User user){
        return userService.get(user);
    }
}
