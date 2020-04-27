package com.czy.user.service;

import com.czy.core.annotation.Auto;
import com.czy.core.annotation.Service;
import com.czy.core.db.service.ICommonService;
import com.czy.user.User;
import com.czy.util.SecretUtil;

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
    public void insert(User user) {
        String ps =user.getPs();
        String newPS =SecretUtil.md5(ps);
        user.setOriginalPS(ps);
        user.setPs(newPS);
        Integer id = commonService.insert(user);
        user.setId(id);
    }
}
