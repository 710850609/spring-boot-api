package me.linbo.web.security.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import me.linbo.web.security.service.param.JwtAuthentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * jwt 生成、解析工具
 * @author LinBo
 * @date 2020-01-13 14:28
 */
@Component
public class JwtBiz {

    private Key key = Keys.hmacShaKeyFor("9KZobY7maVd1lkiPyNh8TzrWMOQSUgCD0FBxtqX62AnGs35fLeEHIpc4jRvJuwLv41tWbrlfUCjP1fmkx1FLOnqjpdTRJEkhd6DmA1LsG5IFoHMyRSIAntHpKCMcZV35".getBytes());

    public String create(UserDetails userInfo) {
        Date expiration = new Date(System.currentTimeMillis() + 1000*60*60*24);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("userName", userInfo.getUsername());
        claims.put("password", userInfo.getPassword());
        claims.put("authorities", userInfo.getAuthorities());
        String jws = Jwts.builder()
                .addClaims(claims)
                .setExpiration(expiration)
                .signWith(key)
                .compact();
        return jws;
    }

    public JwtAuthentication parse(String token) {
        Jws<Claims> jws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
        Claims claims = jws.getBody();
        JwtAuthentication authentication = new JwtAuthentication(token);
        return authentication;
    }

    public static void main(String[] args) {
        JwtBiz jwtBiz = new JwtBiz();
        String s = jwtBiz.create(User.withUsername("123").password("123").authorities("123").build());
        System.out.println(s);
    }
}
