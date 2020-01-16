package me.linbo.web.security.filter;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.service.param.HttpResourceAuthority;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.Optional;

/**
 * Bearer Token认证过滤器
 * @author LinBo
 * @date 2020-01-09 17:38
 */
@Slf4j
public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

    private JwtBiz jwtBiz;

    public BearerTokenAuthenticationFilter(JwtBiz jwtBiz) {
        this.jwtBiz = jwtBiz;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = getJwtToken(header);
        if (token != null) {
            log.info("JWT Token认证...");
            log.info("解析得到token: [{}]", token);
            JwtAuthentication authentication = jwtBiz.parse(token);
            Collection<GrantedAuthority> authorityList = authentication.getAuthorities();
            Optional<GrantedAuthority> first = authorityList.stream().filter(auth -> {
                HttpResourceAuthority ra = (HttpResourceAuthority) auth;
                return new AntPathRequestMatcher(ra.getUri(), ra.getMethod()).matches(request);
            }).findFirst();
            authentication.setAuthenticated(first.isPresent());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
        chain.doFilter(request, response);
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

}
