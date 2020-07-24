package com.czy.car_server.service.impl;

import com.czy.car_server.model.table.User;
import com.czy.car_server.service.IUserService;
import com.czy.core.annotation.bean.Service;
import com.czy.util.text.StringUtil;
import com.czy.util.model.ResultVO;

import static com.czy.util.enums.ResCodeEnum.ArgAnalyExce;
import static com.czy.util.enums.ResCodeEnum.DBExce;

/**
 * @author chenzy
 * 
 * @since 2020-04-27
 */
@Service
public class UserServiceImpl implements IUserService {

    @Override
    public ResultVO regist(User user) {
        if (user == null) {
            return new ResultVO(ArgAnalyExce, "未接收到任何参数");
        }
        var mobile = user.getMobile();
        String originalPs=user.getOriginalPs();
        if (StringUtil.isBlank(mobile)){
            return new ResultVO(DBExce, "未输入帐号！");
        }
        if (StringUtil.isBlank(originalPs)){
            return new ResultVO(DBExce, "未输入密码！");
        }
        /*用户密码加密*/
        user.setPs();
        /*插入数据库*/
        Integer userId =null;// commonService.insert(user);
        if (userId == null || userId.equals(0)) {
            return new ResultVO(DBExce, "注册用户失败：插入数据库异常");
        }
        user.setId(userId);
        /*插入redis*/
        setRedis(user);
        /*返回数据*/
        return new ResultVO(user,"注册成功");
    }

    private Boolean setRedis(User user){
        if (user==null|| StringUtil.isBlank(user.getCode())){
            return false;
        }
        return null;//redisService.set("car:user_"+user.getCode(), user);
    }
    public User getByRedis(String userCode){
        return null;//redisService.get("car:user_"+userCode,User.class);
    }
}
