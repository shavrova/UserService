package com.tms.api.users.config.security.utils;

import com.tms.api.users.data.dto.UserDto;
import com.tms.api.users.data.entity.User;
import com.tms.api.users.data.model.user.enums.RoleEnum;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Component
public class JwtTool {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Value("${jwt.expiration-time}")
    private Long jwtExpiration;

    public String generateToken(UserDto user) {
        log.info("generating token from claims:");
        Map<String, Object> claims = new HashMap<>();
        log.info("userId: " + user.getUserId());
        claims.put("userId", user.getUserId());

        log.info("userFullName: " + user.getUserId());
        claims.put("userFullName", user.getFirstName() + " " + user.getLastName());

        log.info("role: " + RoleEnum.fromCode(user.getRole()));
        claims.put("role", RoleEnum.fromCode(user.getRole()));
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
