package com.veras.mythOrFactLGBT.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtil.class);

    @Value("${supabase.jwt.secret:DefaultSecretKeyForDevEnvironmentWhichIsSuperLongAndSecure}") // Default for dev if not in props
    private String jwtSecretString;

    private Key jwtSecretKey;

    @Value("${jwt.expiration.ms:86400000}") // 24 hours default
    private int jwtExpirationMs;

    @PostConstruct
    public void init() {
        // Ensure the secret key is strong enough for HS256, or use HS512 etc.
        // A simple way to ensure minimum length for common signing algorithms like HS256
        if (jwtSecretString == null || jwtSecretString.getBytes().length < 32) { // Check byte length for HS256
            logger.warn("JWT Secret key from properties is too short or null. Using a default secure key. PLEASE SET a strong 'supabase.jwt.secret' in properties for production.");
            // This default key is for placeholder/development purposes.
            this.jwtSecretKey = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        } else {
            this.jwtSecretKey = Keys.hmacShaKeyFor(jwtSecretString.getBytes());
        }
    }

    public String generateToken(Authentication authentication) {
        UserDetails userPrincipal = (UserDetails) authentication.getPrincipal();
        return generateTokenFromUsername(userPrincipal.getUsername());
    }

    public String generateTokenFromUsername(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs))
                .signWith(jwtSecretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parserBuilder().setSigningKey(jwtSecretKey).build().parseClaimsJws(authToken);
            return true;
        } catch (SecurityException e) {
            logger.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }
}
