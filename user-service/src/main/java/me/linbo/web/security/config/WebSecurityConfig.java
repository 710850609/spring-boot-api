package me.linbo.web.security.config;
import me.linbo.web.security.bll.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

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
    private JwtBiz jwtBiz;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                // 登录接口、错误接口允许访问
                .and().authorizeRequests().antMatchers("/login", "/error").permitAll()
                .anyRequest().authenticated();
        // 不采用spring security默认的认证过滤器 {@link org.springframework.security.config.annotation.web.builders.FilterComparator}
        // 多种登录方式
        // 替换默认用户名密码登录拦截器
        // 账号密码登录
        http.addFilterAt(new NamePasswordAuthenticationFilter(super.authenticationManager()), UsernamePasswordAuthenticationFilter.class);
        // 手机+验证码登录
        http.addFilterAfter(new PhoneVerificationCodeAuthenticationFilter("/phoneLogin"), NamePasswordAuthenticationFilter.class);
        // token验证
        http.addFilterBefore(new BearerTokenAuthenticationFilter(), PhoneVerificationCodeAuthenticationFilter.class);
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        auth.userDetailsService(userDetailBiz)
//            .passwordEncoder(passwordEncoder());
//        auth.authenticationProvider(new JwtAuthenticationProvider(jwtBiz));
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
