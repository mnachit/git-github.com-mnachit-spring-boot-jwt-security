package com.example.jwtspringboot.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.interfaces.DecodedJWT;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import com.example.jwtspringboot.model.entity.User;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.security.Key;
import java.util.Date;
import java.util.concurrent.TimeUnit;

@Component
public class JwtUtil {
    private Key secretKey;
    private final long accessTokenValidity = 60 * 60 * 1000; // 1 hour in milliseconds

    private JwtParser jwtParser;

    @PostConstruct
    public void init() {
        this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
        this.jwtParser = Jwts.parserBuilder().setSigningKey(secretKey).build();

    }

    public String createToken(User user) {
        Claims claims = Jwts.claims().setSubject(user.getEmail());

        claims.put("id", user.getId());
        claims.put("email", user.getEmail());
        claims.put("fullName", user.getFullName());

        Date tokenCreateTime = new Date();
        Date tokenValidity = new Date(tokenCreateTime.getTime() + TimeUnit.DAYS.toMillis(1));

        return Jwts.builder()
                .setClaims(claims)
                .setIssuer("your-application-name")
                .setIssuedAt(tokenCreateTime)
                .setExpiration(tokenValidity)
                .signWith(secretKey, SignatureAlgorithm.HS512)
                .compact();
    }

    // طرق مساعدة للحصول على معلومات إضافية
    private String getCurrentIpAddress() {
        // احصل على عنوان IP الحالي من طلب HTTP
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getRemoteAddr();
    }

    private String getUserAgent() {
        // احصل على معلومات المتصفح
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        return request.getHeader("User-Agent");
    }

    private Claims parseJwtClaims(String token) {
        return jwtParser.parseClaimsJws(token).getBody();
    }

    public Claims resolveClaims(HttpServletRequest req) {
        try {
            String token = resolveToken(req);
            if (token != null) {
                return parseJwtClaims(token);
            }
            return null;
        } catch (Exception ex) {
            req.setAttribute("invalid", ex.getMessage());
            throw ex;
        }
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateClaims(Claims claims) {
        return claims.getExpiration().after(new Date());
    }

    public String getEmail(Claims claims) {
        return claims.getSubject();
    }

    public static Long extractUserId(String cleantoken) {

        String token = cleantoken.replace("Bearer ", "");

        try {
            DecodedJWT jwt = JWT.decode(token);

            return jwt.getClaim("id").asLong();
        } catch (Exception e) {
            System.err.println("Erreur lors du décodage du token: " + e.getMessage());
            return null;
        }
    }

}
