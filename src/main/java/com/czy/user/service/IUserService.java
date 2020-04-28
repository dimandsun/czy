package com.czy.user.service;

import com.czy.user.User;
import com.czy.util.model.ResultVO;

/**
 * @author chenzy
 * @description
 * @since 2020-04-27
 */
public interface IUserService {
    ResultVO insert(User user);

    ResultVO update(User user);

    ResultVO delete(User user);

    ResultVO get(User user);
}
