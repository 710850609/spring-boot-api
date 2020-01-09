package me.linbo.web.security.bll;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.linbo.web.security.service.param.LoginParam;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * JWT认证过滤
 * @author LinBo
 * @date 2020-01-09 17:35
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        LoginParam loginParam = getLoginParam(request);
        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(
                loginParam.getName(), loginParam.getPassword());

        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);

        return this.getAuthenticationManager().authenticate(authRequest);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        super.successfulAuthentication(request, response, chain, authResult);
        Object principal = authResult.getPrincipal();
        System.out.println("认证成功: " + principal);
        String toke = "1234567890";
        response.setHeader("token", toke);
    }

    protected LoginParam getLoginParam(HttpServletRequest request) {
        LoginParam loginParam = (LoginParam) request.getAttribute("loginParam");
        if (loginParam == null) {
            String contentType = request.getContentType();
            MediaType mediaType =  MediaType.parseMediaType(contentType);
            if (MediaType.APPLICATION_JSON.equals(mediaType)
                    || MediaType.APPLICATION_JSON_UTF8.equals(mediaType)) {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    loginParam = objectMapper.readValue(request.getInputStream(), LoginParam.class);
                    request.setAttribute("loginParam", loginParam);
                } catch (Exception e) {}
            } else {
                loginParam = new LoginParam();
                loginParam.setName(super.obtainUsername(request));
                loginParam.setPassword(super.obtainPassword(request));
            }
        }
        return loginParam;
    }

}
