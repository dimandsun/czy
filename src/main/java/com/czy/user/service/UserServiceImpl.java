package com.czy.user.service;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.Service;
import com.czy.core.db.service.ICommonService;
import com.czy.user.User;
import com.czy.util.SecretUtil;
import com.czy.util.enums.ResCodeEnum;
import com.czy.util.model.ResultVO;

import java.util.List;

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
    public ResultVO insert(User user) {
        String ps = user.getPs();
        String newPS = SecretUtil.md5(ps);
        user.setOriginalPS(ps);
        user.setPs(newPS);
        Integer id = commonService.insert(user);
        user.setId(id);
        return new ResultVO<>(user);
    }

    @Override
    public ResultVO update(User user) {
        var whereUser = new User();
        whereUser.setId(user.getId());
        user.setId(null);
        Boolean reuslt = commonService.update(user, whereUser);
        if (reuslt) {
            return new ResultVO();
        } else {
            return new ResultVO(ResCodeEnum.BusInExce, "修改失败");
        }
    }

    @Override
    public ResultVO delete(User user) {
        Boolean result = commonService.delete(user);
        if (result) {
            return new ResultVO();
        } else {
            return new ResultVO(ResCodeEnum.BusInExce, "删除失败");
        }
    }

    @Override
    public ResultVO get(User user) {
        List<User> userList = commonService.getListBean(user);
        return new ResultVO(userList);
    }

}
