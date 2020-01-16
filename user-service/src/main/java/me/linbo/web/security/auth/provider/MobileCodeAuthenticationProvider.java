package me.linbo.web.security.auth.provider;

import me.linbo.web.security.exception.MobileNotFoundException;
import me.linbo.web.security.service.param.MobileCodeAuthentication;
import me.linbo.web.security.service.param.RequestAuthentication;
import me.linbo.web.user.bll.UserBiz;
import me.linbo.web.user.model.User;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.util.Assert;

/**
 * 手机验证码认证逻辑
 * @author LinBo
 * @date 2020-01-14 13:36
 */
public class MobileCodeAuthenticationProvider implements AuthenticationProvider {

    private UserBiz userBiz;

    public MobileCodeAuthenticationProvider(UserBiz userBiz) {
        Assert.notNull(userBiz, "需要传入UserBiz实例");
        this.userBiz = userBiz;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        MobileCodeAuthentication principal = (MobileCodeAuthentication) authentication;
        User user = userBiz.getByMobile(principal.getMobile());
        if (user == null) {
            throw new MobileNotFoundException(principal.getMobile());
        }
        RequestAuthentication requestAuthentication = new RequestAuthentication();
        return requestAuthentication;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.isAssignableFrom(MobileCodeAuthentication.class);
    }
}
