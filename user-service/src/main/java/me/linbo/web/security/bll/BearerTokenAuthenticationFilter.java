package me.linbo.web.security.bll;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT鉴权过滤
 * @author LinBo
 * @date 2020-01-09 17:38
 */
@Slf4j
public class BearerTokenAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        String token = getJwtToken(header);
        if (token == null) {
            chain.doFilter(request, response);
            return;
        }
        log.info("JWT Token: [{}]", token);
        UserDetails userInfo = JwtBiz.parse(token);
        log.info("解析Jwt Token得到用户信息: [{}]", userInfo);
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
        return header.substring(index);
    }

}
