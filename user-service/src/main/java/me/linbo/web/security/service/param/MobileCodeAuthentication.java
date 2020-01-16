package me.linbo.web.security.service.param;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机验证码认证对象
 * @author LinBo
 * @date 2020-01-13 17:27
 */
public class MobileCodeAuthentication extends AbstractAuthenticationToken {

    private String mobile;

    private String code;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public MobileCodeAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public MobileCodeAuthentication(String mobile, String code) {
        super(null);
        this.mobile = mobile;
        this.code = code;
        setAuthenticated(false);
    }

    public String getMobile() {
        return mobile;
    }

    public String getCode() {
        return code;
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }
}
