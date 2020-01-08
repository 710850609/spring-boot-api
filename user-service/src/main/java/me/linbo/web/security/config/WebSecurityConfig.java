package me.linbo.web.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.linbo.web.security.bll.UserDetailBiz;
import me.linbo.web.security.bll.WebSecurityInterceptor;
import me.linbo.web.security.service.param.LoginParam;
import me.linbo.web.user.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationTrustResolver;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.http.HttpServletRequest;
import java.awt.*;

/**
 * @author LinBo
 * @date 2020-01-06 15:47
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailBiz userDetailBiz;

    @Autowired
    private WebSecurityInterceptor webSecurityInterceptor;

//    @Autowired
//    private NamePasswordJsonAuthenticationFilter namePasswordJsonAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests().antMatchers("/css/***", "/js/***", "/fonts/***").permitAll()
                .and().authorizeRequests().antMatchers("/login").permitAll()
                .anyRequest().authenticated();
        http.formLogin().loginProcessingUrl("/admin").usernameParameter("name").passwordParameter("password");
        http.addFilterBefore(webSecurityInterceptor, FilterSecurityInterceptor.class);
        http.addFilterAt(jsonLoginFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailBiz)
            .passwordEncoder(passwordEncoder());
//
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(userDetailBiz);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        auth.authenticationProvider(authProvider);
    }

//    @Override
//    public void setTrustResolver(AuthenticationTrustResolver trustResolver) {
//        super.setTrustResolver(trustResolver);
//    }

//    @Bean
//    @Override
//    protected AuthenticationManager authenticationManager() throws Exception {
//        return super.authenticationManager();
//    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    private UsernamePasswordAuthenticationFilter jsonLoginFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter() {
            @Override
            protected String obtainPassword(HttpServletRequest request) {
                return getLoginParam(request).getPassword();
            }

            @Override
            protected String obtainUsername(HttpServletRequest request) {
                return getLoginParam(request).getName();
            }

            protected LoginParam getLoginParam(HttpServletRequest request) {
                MediaType contentType = getRequestContentType(request);
                if (MediaType.APPLICATION_JSON.equals(contentType)
                        || MediaType.APPLICATION_JSON_UTF8.equals(contentType)) {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        return objectMapper.readValue(request.getInputStream(), LoginParam.class);
                    } catch (Exception e) {}
                }
                LoginParam loginParam = new LoginParam();
                loginParam.setName(super.obtainUsername(request));
                loginParam.setPassword(super.obtainPassword(request));
                return loginParam;
            }

            protected MediaType getRequestContentType(HttpServletRequest request) {
                String contentType = request.getContentType();
                return MediaType.parseMediaType(contentType);
            }

        };
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }
}
