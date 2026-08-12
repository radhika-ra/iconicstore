package com.radhika.iconicstore;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {


        // Store this in application.yml / an environment variable, never hardcode
        // it in source for a real deployment. Must be at least 256 bits for HS256.
        @Value("${jwt.secret}")
        private String secretKey;

        @Value("${jwt.expiration-ms:86400000}") // default: 24 hours
        private long expirationMs;

        private Key getSigningKey() {
            return Keys.hmacShaKeyFor(secretKey.getBytes());
        }

        public String generateToken(UserDetails userDetails) {
            Date now = new Date();
            Date expiry = new Date(now.getTime() + expirationMs);

            return Jwts.builder()
                    .setSubject(userDetails.getUsername())
                    .claim("role", userDetails.getAuthorities().iterator().next().getAuthority())
                    .setIssuedAt(now)
                    .setExpiration(expiry)
                    .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                    .compact();
        }

        public String extractUsername(String token) {
            return extractClaims(token).getSubject();
        }

        public boolean isTokenValid(String token, UserDetails userDetails) {
            String username = extractUsername(token);
            return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
        }

        private boolean isTokenExpired(String token) {
            return extractClaims(token).getExpiration().before(new Date());
        }

        private Claims extractClaims(String token) {
            return Jwts.parser()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        }
    }


