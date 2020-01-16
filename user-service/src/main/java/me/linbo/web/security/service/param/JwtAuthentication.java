package me.linbo.web.security.service.param;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthentication;

import java.util.Date;
import java.util.List;

/**
 * jwt认证数据，也可以复用 {@link BearerTokenAuthentication}
 * @author LinBo
 * @date 2020-01-14 14:02
 */
@Getter
public class JwtAuthentication extends AbstractAuthenticationToken {

    private String id;

    private Date expiration;

    private Date notBefore;

    private String userName;

    public JwtAuthentication(String id, String userName, Date expiration, Date notBefore, List<HttpResourceAuthority> authorities) {
        super(authorities);
        this.id = id;
        this.userName = userName;
        this.expiration = expiration;
        this.notBefore = notBefore;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public List<HttpResourceAuthority> getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return null;
    }

}
