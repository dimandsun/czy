package com.czy.car.service;

import com.czy.car.model.table.User;
import com.czy.util.model.ResultVO;


/**
 * @author chenzy
 * @description
 * @since 2020-04-27
 */
public interface IUserService {
    ResultVO regist(User user);
}
