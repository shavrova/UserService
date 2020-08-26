package com.tms.api.users.config.security.utils;

import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.data.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class JwtTool {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private Long jwtExpiration;

    public String generateToken(UserDto user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("userId", user.getUserId());
        claims.put("userFirstName", user.getFirstName());
        claims.put("userLastName", user.getLastName());
        claims.put("created", new Date());
        return generateToken(claims);
    }

    public Claims getClaims(String token) {
        return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody();
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + jwtExpiration * 1000);
    }
}
