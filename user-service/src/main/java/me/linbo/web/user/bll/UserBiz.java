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

    public User getByName(String name) {
        User user = super.baseMapper.selectOne(lambdaQuery().eq(User::getLoginName, name).getWrapper());
        return user;
    }

    public User getByMobile(String mobile) {
        User user = super.baseMapper.selectOne(lambdaQuery().eq(User::getMobile, mobile).getWrapper());
        return user;
    }

}