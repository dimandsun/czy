package com.czy.car.controller;

import com.czy.car.model.User;
import com.czy.car.service.IUserService;
import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.Controller;
import com.czy.core.annotation.mapping.Mapping;
import com.czy.core.annotation.mapping.PostMapping;
import com.czy.core.service.ICommonService;
import com.czy.util.model.ResultVO;

/**
 * @author chenzy
 * @since 2020-06-09
 */
@Controller
@Mapping("user")
public class UserController {
    @Auto
    private ICommonService commonService;

    @Auto
    private IUserService userService;

    /**
     * 注册
     * @return
     */
    @PostMapping
    public ResultVO regist(User user){
        return userService.regist(user);
    }
}
