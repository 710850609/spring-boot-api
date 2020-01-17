package me.linbo.web.security.auth.provider;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * @author LinBo
 * @date 2020-01-17 10:55
 */
@Slf4j
public class BearerTokenAuthenticationProvider implements AuthenticationProvider {

    private JwtBiz jwtBiz;

    public BearerTokenAuthenticationProvider(JwtBiz jwtBiz) {
        this.jwtBiz = jwtBiz;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("JWT Token认证...");
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;
        String token = jwtAuthentication.getToken();
        JwtAuthentication parse = jwtBiz.parse(token);
        parse.setAuthenticated(false);
        return parse;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.isAssignableFrom(authentication);
    }
}
