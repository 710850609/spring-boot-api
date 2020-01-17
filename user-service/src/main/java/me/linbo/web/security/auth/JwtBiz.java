package me.linbo.web.security.auth;

import cn.hutool.core.bean.BeanUtil;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import me.linbo.web.security.exception.WebSecurityException;
import me.linbo.web.security.service.param.HttpResourceAuthority;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
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
@Slf4j
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
        return create(userInfo.getUsername(), userInfo.getAuthorities(), expirationTime);
    }

    public String createForRefresh(UserDetails userDetails) {
        return create(userDetails.getUsername(), userDetails.getAuthorities(), expirationTime * 2);
    }

    private String create(String userName, Collection<? extends GrantedAuthority> authorities, Long expiration) {
        Date expirationDate = new Date(System.currentTimeMillis() + expiration);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("userName", userName);
        claims.put("authorities", authorities);
        String jws = Jwts.builder()
                .addClaims(claims)
                .setExpiration(expirationDate)
                .setNotBefore(null)
                .signWith(key)
                // 这里生成唯一id，如果需要模拟session，做到唯一终端登录，可以根据此属性，进行限制判断
                .setId(UUID.randomUUID().toString().replace("-", ""))
                .compact();
        return jws;
    }

    public JwtAuthentication parse(String token) {
        try {
            Jws<Claims> jws = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token);
            List<HttpResourceAuthority> authorityList = new ArrayList<>();
            jws.getBody().get("authorities", List.class).forEach(v -> {
                HttpResourceAuthority resourceAuthority = new HttpResourceAuthority();
                BeanUtil.fillBeanWithMap((Map<?, ?>) v, resourceAuthority, true);
                authorityList.add(resourceAuthority);
            });
            String id = jws.getBody().getId();
            Date expiration = jws.getBody().getExpiration();
            Date notBefore = jws.getBody().getNotBefore();
            String userName = jws.getBody().get("userName", String.class);
            JwtAuthentication authentication = new JwtAuthentication(token, userName, authorityList);
            return authentication;
        } catch (ExpiredJwtException e1) {
            log.warn("token已失效", e1);
            throw WebSecurityException.TOKEN_EXPIRED;
        } catch (UnsupportedJwtException | MalformedJwtException | SignatureException e2) {
            log.warn("token格式错误", e2);
            throw WebSecurityException.TOKEN_ERROR;
        }
    }

}
