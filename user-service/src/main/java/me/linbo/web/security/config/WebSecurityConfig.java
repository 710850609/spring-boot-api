package me.linbo.web.security.config;

import me.linbo.web.security.auth.JwtBiz;
import me.linbo.web.security.auth.provider.BearerTokenAuthenticationProvider;
import me.linbo.web.security.auth.provider.MobileCodeAuthenticationProvider;
import me.linbo.web.security.auth.provider.UserDetailBiz;
import me.linbo.web.security.filter.BearerTokenAuthenticationFilter;
import me.linbo.web.security.filter.MobileCodeAuthenticationFilter;
import me.linbo.web.security.filter.NamePasswordAuthenticationFilter;
import me.linbo.web.security.filter.RefreshTokenAuthenticationFilter;
import me.linbo.web.user.bll.UserBiz;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * http安全访问配置
 * @author LinBo
 * @date 2020-01-06 15:47
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailBiz userDetailBiz;

    @Autowired
    private UserBiz userBiz;

    @Autowired
    private JwtBiz jwtBiz;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 登录接口、错误接口允许访问
                .and().authorizeRequests().antMatchers("/login", "/error").permitAll()
                .anyRequest().authenticated();
        // 不采用spring security默认的认证过滤器链 {@link org.springframework.security.config.annotation.web.builders.FilterComparator}
        // 多种登录方式
        // 替换默认用户名密码认证拦截器
        // 手机+验证码认证
        http.addFilterAt(new MobileCodeAuthenticationFilter("/phoneLogin", super.authenticationManager()), NamePasswordAuthenticationFilter.class);
        // 账号密码认证
        http.addFilterAfter(new NamePasswordAuthenticationFilter(jwtBiz, super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 刷新token
        http.addFilterAfter(new RefreshTokenAuthenticationFilter("/refreshToken", jwtBiz, userDetailBiz, super.authenticationManager()), NamePasswordAuthenticationFilter.class);
        // token认证
        http.addFilterAfter(new BearerTokenAuthenticationFilter(jwtBiz, super.authenticationManager()), RefreshTokenAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 这里不采用下面 auth.authenticationProvider 的方式编写，是为了提供常用用户名密码登录快捷写法demo
        auth.userDetailsService(userDetailBiz).passwordEncoder(passwordEncoder());
        auth.authenticationProvider(new MobileCodeAuthenticationProvider(userBiz));
        auth.authenticationProvider(new BearerTokenAuthenticationProvider(jwtBiz));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
