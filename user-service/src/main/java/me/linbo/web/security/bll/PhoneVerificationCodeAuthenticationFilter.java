package me.linbo.web.security.bll;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.service.param.MobileCodeAuthentication;
import me.linbo.web.security.service.param.MobileCodeLoginParam;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author LinBo
 * @date 2020-01-13 16:05
 */
@Slf4j
public class PhoneVerificationCodeAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    public PhoneVerificationCodeAuthenticationFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        log.info("手机验证码登录...");
        MobileCodeLoginParam params = getRequestParams(request);
        MobileCodeAuthentication authentication = new MobileCodeAuthentication(params.getMobile(), params.getCode());

        return this.getAuthenticationManager().authenticate(authentication);
    }

    private MobileCodeLoginParam getRequestParams(HttpServletRequest request) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(request.getInputStream(), MobileCodeLoginParam.class);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

    }
}
