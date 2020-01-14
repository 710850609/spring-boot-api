package me.linbo.web.security.service.param;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

import java.util.Collection;

/**
 * jwt认证数据，也可以复用 {@link BearerTokenAuthentication}
 * @author LinBo
 * @date 2020-01-14 14:02
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    private String token;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JwtAuthentication(Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
    }

    public JwtAuthentication(String token) {
        super(null);
        this.token = token;
    }

    public String getToken() {
        return token;
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
