package me.linbo.web.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.service.param.NamePasswordLoginParam;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 名字和密码 认证过滤
 * @author LinBo
 * @date 2020-01-09 17:35
 */
@Slf4j
public class NamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private JwtBiz jwtBiz;

    public NamePasswordAuthenticationFilter(JwtBiz jwtBiz, AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
        this.jwtBiz = jwtBiz;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("账号密码认证...");
        NamePasswordLoginParam loginParam = getLoginParam(request);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginParam.getName(), loginParam.getPassword());
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 认证成功，输出token
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        log.info("账号密码认证成功: [{}]", userDetails);
        String token = jwtBiz.create(userDetails);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"token\":\"" + token + "\"}");
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (super.eventPublisher != null) {
            super.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }
    }

    /**
     * 获取登录参数，这里只支持json格式数据
     * @Author LinBo
     * @param request
     * @return {@link NamePasswordLoginParam}
     **/
    private NamePasswordLoginParam getLoginParam(HttpServletRequest request) {
        NamePasswordLoginParam loginParam = new NamePasswordLoginParam();
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            loginParam = objectMapper.readValue(request.getInputStream(), NamePasswordLoginParam.class);
        } catch (Exception e) {}
        return loginParam;
    }

}
