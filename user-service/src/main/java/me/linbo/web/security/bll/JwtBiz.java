package me.linbo.web.security.bll;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import me.linbo.web.core.entity.UserInfo;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author LinBo
 * @date 2020-01-13 14:28
 */
@Component
public class JwtBiz implements JwtDecoder {

    public static final String REQUEST_HEADER = "";

    static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    public static String create(UserDetails userInfo) {
        Date expiration = new Date(System.currentTimeMillis() + 1000*60*60*24);
        Map<String, Object> claims = new HashMap<>(1);
        claims.put("userName", userInfo.getUsername());
        String jws = Jwts.builder()
                .addClaims(claims)
                .setExpiration(expiration)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
        return jws;
    }

    public static UserDetails parse(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        return User.withUsername(claims.get("userName", String.class)).build();
    }

    @Override
    public Jwt decode(String token) throws JwtException {
        Jws<Claims> claimsJws = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token);
        Jwt jwt = Jwt.withTokenValue(token)
//                .claims(claimsJws.getBody())
//                .header(claimsJws.getHeader())
                .build();
        return jwt;
    }
}
