package me.linbo.web.security.bll;

import me.linbo.web.core.util.AssertUtil;
import me.linbo.web.user.bll.UserBiz;
import me.linbo.web.user.exception.UserException;
import me.linbo.web.user.model.User;
import me.linbo.web.user.service.result.LoginResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author LinBo
 * @date 2020-01-06 14:52
 */
@Service
public class LoginBiz {

    @Autowired
    private UserBiz userBiz;

    public LoginResult login(String name, String password) {
        // 认证
        User user = userBiz.getByNameAndPassword(name, password);
        AssertUtil.notNull(user, UserException.USER_NOT_EXISTS);
        // 授权
        return null;
    }
}
