package me.linbo.web.security.filter;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Bearer Token认证过滤器
 * @author LinBo
 * @date 2020-01-09 17:38
 */
@Slf4j
public class BearerTokenAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private JwtBiz jwtBiz;

    public BearerTokenAuthenticationFilter(JwtBiz jwtBiz, AuthenticationManager authenticationManager) {
        super("/**");
        setAuthenticationManager(authenticationManager);
        super.setContinueChainBeforeSuccessfulAuthentication(true);
        this.jwtBiz = jwtBiz;
    }

    private String getJwtToken(String header) {
        if (header == null) {
            return null;
        }
        header = header.trim();
        int index = header.indexOf("Bearer ");
        if (index != 0) {
            return null;
        }
        return header.substring(7);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = getJwtToken(header);
        if (token != null) {
            log.info("解析得到token: [{}]", token);
            JwtAuthentication authentication = new JwtAuthentication(token, null, null);
            Authentication authenticate = this.getAuthenticationManager().authenticate(authentication);
            // 成功验证后需要放入到security上下文，防止 后面 AnonymousAuthenticationFilter 进行默认角色校验而不通过
            SecurityContextHolder.getContext().setAuthentication(authenticate);
            return authenticate;
        }
        return null;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = getJwtToken(header);
        if (token != null) {
            log.info("解析得到token: [{}]", token);
            JwtAuthentication authentication = new JwtAuthentication(token, null, null);
            Authentication authenticate = this.getAuthenticationManager().authenticate(authentication);
            // 成功验证后需要放入到security上下文，防止 后面 AnonymousAuthenticationFilter 进行默认角色校验而不通过
            SecurityContextHolder.getContext().setAuthentication(authenticate);
        }
        chain.doFilter(req, res);
    }
}