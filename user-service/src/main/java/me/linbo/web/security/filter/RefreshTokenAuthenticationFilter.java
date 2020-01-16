package me.linbo.web.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.core.util.AssertUtil;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.exception.WebSecurityException;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 刷新token认证
 * @author LinBo
 * @date 2020-01-16 12:50
 */
@Slf4j
public class RefreshTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtBiz jwtBiz;

    public RefreshTokenAuthenticationFilter(String defaultFilterProcessesUrl, JwtBiz jwtBiz) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
        this.jwtBiz = jwtBiz;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("刷新token认证...");
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(request.getInputStream(), Map.class);
        String refreshToke = (String) map.get("token");
        AssertUtil.notBlank(refreshToke, WebSecurityException.TOKEN_EMPTY);
        JwtAuthentication authentication = jwtBiz.parse(refreshToke);
        Date notBefore = authentication.getNotBefore();
        Date expiration = authentication.getExpiration();
        Date now = new Date();
        if (notBefore != null && notBefore.before(now)) {
            throw WebSecurityException.TOKEN_NOT_ACTIVE;
        }
        AssertUtil.mustTrue(expiration != null, WebSecurityException.TOKEN_ERROR);
        AssertUtil.mustTrue(now.before(expiration), WebSecurityException.TOKEN_EXPIRED);
        request.getRequestDispatcher("/login").forward(request, response);
        return authentication;
    }

}
