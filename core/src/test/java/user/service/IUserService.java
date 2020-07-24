package user.service;

import user.model.User;
import com.czy.util.model.ResultVO;

import java.util.List;

/**
 * @author chenzy
 * 
 * @since 2020-04-27
 */
public interface IUserService {
    ResultVO insert(User user);

    ResultVO update(User user);

    ResultVO delete(User user);

    ResultVO<List<User>> get(User user);
}
