package me.linbo.web.security.filter;

import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

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
        log.info("JWT认证...");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = getJwtToken(header);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        log.info("解析得到token: [{}]", token);
        JwtAuthentication authentication = jwtBiz.parse(token);
        // TODO 鉴权uri
        SecurityContextHolder.getContext().setAuthentication(authentication);
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
