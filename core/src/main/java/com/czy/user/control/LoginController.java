package com.czy.user.control;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.Controller;
import com.czy.core.annotation.GetMapping;
import com.czy.user.model.User;
import com.czy.user.service.IUserService;
import com.czy.util.SecretUtil;
import com.czy.util.enums.ResCodeEnum;
import com.czy.util.model.ResultVO;

import java.util.List;

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
    public Object login(User user){
        String ps=SecretUtil.md5(user.getPs());
        user.setPs(ps);
        ResultVO<List<User>> userListResult=userService.get(user);
        if (userListResult.isNotNormal()){
            return userListResult;
        }
        List<User> userList = userListResult.getData();
        if (userList==null||userList.isEmpty()){
            return new ResultVO<>(ResCodeEnum.BusInExce,"帐号或者密码错误！");
        }
        if (userList.size()>1){
            return new ResultVO<>(ResCodeEnum.BusInExce,"查询到多个用户！");
        }
        User user1 = userList.get(0);
        if (user1!=null){
            return new ResultVO<>(ResCodeEnum.Normal,"登录成功");
        }
        return new ResultVO<>(ResCodeEnum.UnknownExce,"登录失败：未知异常");
    }
}
