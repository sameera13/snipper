package com.sinppr.snippets.util;

import io.jsonwebtoken.*;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET = System.getenv("JWT_SECRET");
    private static final long EXPIRATION_MS = 24L * 60 * 60 * 1000; // 24 hours

    public static String generateToken(String subject) {
        if (SECRET == null || SECRET.isBlank()) {
            throw new RuntimeException("JWT_SECRET environment variable not set");
        }
        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiry)
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes())
                .compact();
    }

    public static String getSubject(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // ✅ Added method for compatibility with filter
    public static String extractUsername(String token) {
        return getSubject(token);
    }

    // ✅ Added method for compatibility with filter
    public static boolean isTokenExpired(String token) {
        Date expiration = Jwts.parser()
                .setSigningKey(SECRET.getBytes())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
        return expiration.before(new Date());
    }

    public static boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(SECRET.getBytes()).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException ex) {
            return false;
        }
    }

    // ✅ Updated to use username directly
    public static boolean validateToken(String token, String username) {
        final String tokenUsername = extractUsername(token);
        return (tokenUsername.equals(username) && !isTokenExpired(token));
    }
}
