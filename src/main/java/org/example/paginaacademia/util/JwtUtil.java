package org.example.paginaacademia.util;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class JwtUtil {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expirationMs}")
    private int expMs;

    public String generateToken(UserDetails user){
        return Jwts.builder()
                .subject(user.getUsername())
                .claim("role", user.getAuthorities().stream().findFirst().get().getAuthority())
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + expMs))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8)) , SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractUsername(String token) {
        SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        return Jwts
                .parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

    }
}