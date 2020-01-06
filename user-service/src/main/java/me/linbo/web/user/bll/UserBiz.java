package me.linbo.web.user.bll;


import me.linbo.web.core.bll.BaseBiz;
import me.linbo.web.user.mapper.UserMapper;
import me.linbo.web.user.model.User;
import org.springframework.stereotype.Service;

/**
 * @author LinBo
 * @date 2019-11-25 14:24
 */
@Service
public class UserBiz extends BaseBiz<UserMapper, User> {

    public User getByNameAndPassword(String name, String password) {
        User user = super.baseMapper.selectOne(lambdaQuery().eq(User::getLoginName, name).eq(User::getPassword, password).getWrapper());
        return user;
    }

}
