package me.linbo.web.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.service.param.MobileCodeAuthentication;
import me.linbo.web.security.service.param.MobileCodeLoginParam;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.util.Assert;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 手机、验证码认证过滤器
 * @author LinBo
 * @date 2020-01-13 16:05
 */
@Slf4j
public class MobileCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    public MobileCodeAuthenticationFilter(String defaultFilterProcessesUrl, AuthenticationManager authenticationManager) {
        super(defaultFilterProcessesUrl);
        super.setAuthenticationManager(authenticationManager);
        Assert.hasText(defaultFilterProcessesUrl, "认证过滤器处理url为空");
        Assert.notNull(authenticationManager, "需要传入AuthenticationManager实例");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("手机验证码认证...");
        MobileCodeLoginParam params = getRequestParams(request);
        MobileCodeAuthentication authentication = new MobileCodeAuthentication(params.getMobile(), params.getCode());
        return this.getAuthenticationManager().authenticate(authentication);
    }

    private MobileCodeLoginParam getRequestParams(HttpServletRequest request) throws IOException {
        try {
            return OBJECT_MAPPER.readValue(request.getInputStream(), MobileCodeLoginParam.class);
        } catch (Exception e) {}
        return new MobileCodeLoginParam();
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("手机验证码认证成功: [{}]", authResult);
        String token = "eyJhbGciOiJIUzUxMiJ9.eyJ1c2VyTmFtZSI6IjEyMyIsInBhc3N3b3JkIjoiMTIzIiwiYXV0aG9yaXRpZXMiOlt7ImF1dGhvcml0eSI6IjEyMyJ9XSwiZXhwIjoxNTc5MDUzOTY3fQ.N9i1caSHxIAGV9Nt1ymdIKIkrZf_58aNbK04xjsR11CnKtnQUxJK5JR7JnWIKUq5QOaUWFLq2Mvvhhxz4hKt4Q";
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"token\":\"" + token + "\"}");
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (super.eventPublisher != null) {
            super.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }
    }
}
