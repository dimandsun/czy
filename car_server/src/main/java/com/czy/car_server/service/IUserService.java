package com.czy.car_server.service;

import com.czy.car_server.model.table.User;
import com.czy.util.model.ResultVO;


/**
 * @author chenzy
 * @description
 * @since 2020-04-27
 */
public interface IUserService {
    ResultVO regist(User user);
}
