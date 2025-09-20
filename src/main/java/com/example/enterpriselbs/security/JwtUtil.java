package com.example.enterpriselbs.security;

import com.example.enterpriselbs.infrastructure.entities.StaffEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;


@Component
public class JwtUtil {
    @Value("${jwt.expires_in_seconds}")
    private long tokenExpiresInSeconds = 1800;

    @Value("${jwt.secret}")
    private String secret;

    public String generateToken(StaffEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(StaffEntity.USERNAME, user.getUsername());
        claims.put(StaffEntity.ROLE, user.getRole());

        return Jwts.builder()
                .claims(claims)
                .subject(user.getUsername())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + tokenExpiresInSeconds * 1000))
                .signWith(getSigningKey())
                .compact();
    }

    private Key getSigningKey() {
        if (secret.length() < 32) {
            throw new IllegalStateException("JWT secret must be at least 256 bits");
        }
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
    private Claims getAllClaimsFromToken(String token) {
        return Jwts.parser()
                .verifyWith((SecretKey) getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
    public Claims debugGetAllClaimsFromToken(String token) {
        return getAllClaimsFromToken(token);
    }

    public <T> T getClaimFromToken(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = getAllClaimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    public String extractUsername(String token) {
        return getClaimFromToken(token, Claims::getSubject);
    }

    public String extractRole(String token) {
        return (String) getAllClaimsFromToken(token).get(StaffEntity.ROLE);
    }

    public Date getExpirationDateFromToken(String token) {
        return getClaimFromToken(token, Claims::getExpiration);
    }

    public boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public boolean isTokenValid(String token) {
        return !isTokenExpired(token);
    }
}
