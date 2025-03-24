package com.codit.blog.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import org.springframework.stereotype.Component;

@Component
public class JwtUtil {

    // 서비 암호 키 방식
    private static final Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    //ID 추출
    public String extractUserId(String token) {
        return extractClaim(token,Claims::getSubject);
    }

    // 모든 클레임 추출
    public Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    //특정 클레임 추출
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    // 토큰 생성
    public String generateToken(String userId) {
        Map<String, Object> claims = new HashMap<>();
        return  creatToken(claims, userId);
    }

    // 통큰 생성 내부
    public String creatToken(Map<String, Object> claims, String userId) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userId)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365 * 100))
                .signWith(key)
                .compact();
    }

    // 유효성 검사
    public Boolean verifyToken(String token) {
        try{
            return !isTokenExpired(token);
        }catch(Exception e){
            return false;
        }
    }

    // 유효성 만료 확인
    public Boolean isTokenExpired(String token) {
        Date expirationDate =getExpirationDate(token);
        return expirationDate.before(new Date());
    }

    // 만료일 추출
    public Date getExpirationDate(String token) {
        return extractAllClaims(token).getExpiration();
    }

}
