package me.linbo.web.security.bll;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.service.param.NamePasswordLoginParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.InteractiveAuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

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

    public NamePasswordAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        log.info("账号密码登录...");
        NamePasswordLoginParam loginParam = getLoginParam(request);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginParam.getName(), loginParam.getPassword());
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        System.out.println("认证成功: " + userDetails);
        String token = JwtBiz.create(userDetails);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"token\":\"" + token + "\"}");
        SecurityContextHolder.getContext().setAuthentication(authResult);
        if (super.eventPublisher != null) {
            super.eventPublisher.publishEvent(new InteractiveAuthenticationSuccessEvent(
                    authResult, this.getClass()));
        }
    }

    private NamePasswordLoginParam getLoginParam(HttpServletRequest request) {
        NamePasswordLoginParam loginParam = (NamePasswordLoginParam) request.getAttribute("loginParam");
        if (loginParam == null) {
            String contentType = request.getContentType();
            MediaType mediaType =  MediaType.parseMediaType(contentType);
            if (MediaType.APPLICATION_JSON.equals(mediaType)
                    || MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    loginParam = objectMapper.readValue(request.getInputStream(), NamePasswordLoginParam.class);
                } catch (Exception e) {}
            } else {
                loginParam = new NamePasswordLoginParam();
                loginParam.setName(super.obtainUsername(request));
                loginParam.setPassword(super.obtainPassword(request));
            }
        }
        return loginParam;
    }

}
