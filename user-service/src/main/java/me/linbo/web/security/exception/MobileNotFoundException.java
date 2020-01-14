package me.linbo.web.security.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @author LinBo
 * @date 2020-01-14 16:09
 */
public class MobileNotFoundException extends AuthenticationException {

    public MobileNotFoundException(String msg) {
        super(msg);
    }
}
