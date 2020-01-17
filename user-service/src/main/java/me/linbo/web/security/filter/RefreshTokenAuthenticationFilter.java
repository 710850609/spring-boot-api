package me.linbo.web.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.core.util.AssertUtil;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.auth.provider.UserDetailBiz;
import me.linbo.web.security.exception.WebSecurityException;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

/**
 * 刷新token认证
 * @author LinBo
 * @date 2020-01-16 12:50
 */
@Slf4j
public class RefreshTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtBiz jwtBiz;

    private UserDetailBiz userDetailBiz;

    public RefreshTokenAuthenticationFilter(String defaultFilterProcessesUrl, JwtBiz jwtBiz, UserDetailBiz userDetailBiz, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(defaultFilterProcessesUrl, HttpMethod.POST.name()));
        setAuthenticationManager(authenticationManager);
        this.jwtBiz = jwtBiz;
        this.userDetailBiz = userDetailBiz;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("刷新token认证...");
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(request.getInputStream(), Map.class);
        String refreshToke = (String) map.get("token");
        AssertUtil.notBlank(refreshToke, WebSecurityException.TOKEN_EMPTY);
        JwtAuthentication authentication = new JwtAuthentication(refreshToke, null, null);
        Authentication authenticate = this.getAuthenticationManager().authenticate(authentication);
        // 成功验证后需要放入到security上下文，防止 后面 AnonymousAuthenticationFilter 进行默认角色校验而不通过
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        return authenticate;
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        JwtAuthentication authentication = (JwtAuthentication) authResult;
        String userName = authentication.getUserName();
        UserDetails details = userDetailBiz.loadUserByUsername(userName);
        log.info("刷新token认证成功: [{}]", details);
        String token = jwtBiz.create(details);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"token\":\"" + token + "\"}");
    }

}
