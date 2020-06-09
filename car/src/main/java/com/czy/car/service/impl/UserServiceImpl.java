package com.czy.car.service.impl;

import com.czy.car.model.User;
import com.czy.car.service.IUserService;
import com.czy.core.annotation.Auto;
import com.czy.core.annotation.bean.Service;
import com.czy.core.service.ICommonService;
import com.czy.util.model.ResultVO;


import static com.czy.util.enums.ResCodeEnum.ArgAnalyExce;
import static com.czy.util.enums.ResCodeEnum.DBExce;

/**
 * @author chenzy
 * @description
 * @since 2020-04-27
 */
@Service
public class UserServiceImpl implements IUserService {
    @Auto
    private ICommonService commonService;
    @Override
    public ResultVO regist(User user) {
        if (user==null){
            return new ResultVO(ArgAnalyExce,"未接收到任何参数");
        }
        /*用户密码加密*/
        user.setPS();
        /*插入数据库*/
        Integer userId = commonService.insert(user);
        if (userId==null||userId.equals(0)){
            return new ResultVO(DBExce,"注册用户失败：插入数据库异常");
        }
        /*插入redis*/
        user.setId(userId);
        return new ResultVO(user);
    }
}
