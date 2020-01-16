package me.linbo.web.security.auth;

import cn.hutool.core.bean.BeanUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.linbo.web.security.service.param.HttpResourceAuthority;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.security.Key;
import java.util.*;

/**
 * jwt 生成、解析工具
 * @author LinBo
 * @date 2020-01-13 14:28
 */
@Component
public class JwtBiz {

    @Value("${me.linbo.web.security-key}")
    private String securityKey;

    @Value("#{${me.linbo.web.expiration-time}}")
    private Long expirationTime;

    private Key key;

    @PostConstruct
    public void init() {
        Assert.hasText(securityKey, "配置[me.linbo.web.security-key]为空");
        Assert.notNull(expirationTime, "配置[me.linbo.web.expiration-time]为空");
        this.key = Keys.hmacShaKeyFor(securityKey.getBytes());
    }

    public String create(UserDetails userInfo) {
        Date expiration = new Date(System.currentTimeMillis() + expirationTime);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("userName", userInfo.getUsername());
        claims.put("password", userInfo.getPassword());
        claims.put("authorities", userInfo.getAuthorities());
        String jws = Jwts.builder()
                .addClaims(claims)
                .setExpiration(expiration)
                .signWith(key)
                // 这里生成唯一id，如果需要模拟session，做到唯一终端登录，可以根据此属性，进行限制判断
                .setId(UUID.randomUUID().toString().replace("-", ""))
                .compact();
        return jws;
    }

    public JwtAuthentication parse(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
        List<HttpResourceAuthority> authorityList = new ArrayList<>();
        jws.getBody().get("authorities", List.class).forEach(v -> {
            HttpResourceAuthority resourceAuthority = new HttpResourceAuthority();
            BeanUtil.fillBeanWithMap((Map<?, ?>) v, resourceAuthority, true);
            authorityList.add(resourceAuthority);
        });
        JwtAuthentication authentication = new JwtAuthentication(jws.getBody().getId(), authorityList);
        return authentication;
    }

}
