package me.linbo.web.security.service.param;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

import java.util.Collection;
import java.util.List;

/**
 * jwt认证数据，也可以复用 {@link BearerTokenAuthentication}
 * @author LinBo
 * @date 2020-01-14 14:02
 */
@Getter
public class JwtAuthentication extends AbstractAuthenticationToken {

    private String token;

    private String userName;

    private String id;

    public JwtAuthentication(String token, String userName, List<HttpResourceAuthority> authorities) {
        super(authorities);
        this.token = token;
        this.userName = userName;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return super.getAuthorities();
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
