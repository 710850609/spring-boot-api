package me.linbo.web.security.service.param;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

import java.util.List;

/**
 * jwt认证数据，也可以复用 {@link BearerTokenAuthentication}
 * @author LinBo
 * @date 2020-01-14 14:02
 */
public class JwtAuthentication extends AbstractAuthenticationToken {

    private String id;

    private String token;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the principal
     *                    represented by this authentication object.
     */
    public JwtAuthentication(String id, List<HttpResourceAuthority> authorities) {
        super(authorities);
        this.id = id;
    }

    public void setId(String id) {
        this.id = id;
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
