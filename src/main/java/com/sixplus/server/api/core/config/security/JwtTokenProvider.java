package com.sixplus.server.api.core.config.security;

import com.sixplus.server.api.user.model.AuthRequestDTO;
import com.sixplus.server.api.user.model.MemberVo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@Slf4j
@Component
public class JwtTokenProvider {
    @Value("${security.jwt.token.secret-key:secret}")
    private String secretKey;
    @Value("${security.jwt.token.valid-key:valid}")
    private List<String> validKeyList;
    @Value("${security.jwt.token.app-key:app}")
    private List<String> appKeyList;

    @Value("${security.jwt.token.expire-length:86400000}") // 24시간
    private long validityInMilliseconds; // 24h

    @Value("${security.jwt.token.refresh-expire-length:604800000}") // 7일
    private long refreshValidityInMilliseconds; // 7d

    private Key key;

    public static final String AUTHORIZATION_HEADER = "Authorization";

    @PostConstruct
    protected void init() {
        byte[] keyBytes = secretKey.getBytes(StandardCharsets.UTF_8);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .setAllowedClockSkewSeconds(60) // 60초 시계 오차 허용
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String GenerateToken(AuthRequestDTO dto) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("dto", dto);
        return createToken(claims, dto.getLoginId());
    }

    private String createToken(Map<String, Object> claims, String username) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + validityInMilliseconds))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Key getSignKey() {
        byte[] keyBytes = Decoders.BASE64.decode(this.secretKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String createToken(MemberVo info) {
        Claims claims = Jwts.claims().setSubject(info.getUsername());
        claims.put("userInfo in createToken", info);

        Date now = new Date();
        Date validity = new Date(now.getTime() + validityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public String createRefreshToken(MemberVo info) {
        Claims claims = Jwts.claims().setSubject(info.getUsername());
        claims.put("userInfo in createRefreshToken", info);

        Date now = new Date();
        Date validity = new Date(now.getTime() + refreshValidityInMilliseconds);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        MemberVo userInfo = this.getUserInfo(token);
        return new UsernamePasswordAuthenticationToken(userInfo, "", userInfo.getAuthorities());
    }

    public MemberVo getUserInfo(String token) {
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();

        try {
            return new MemberVo(claims);
        } catch (Exception e) {
            log.error("MemberVo 파싱 오류");
            return null;
        }
    }

    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.replace("Bearer ", "");
        }
        return null;
    }

    public boolean resolveAppkey(HttpServletRequest req) {
        String secret_key = req.getHeader("X-Secret-Key");
        log.debug("Get Header X-Secret-Key Data {}", secret_key);
        if (secret_key != null && validKeyList.contains(secret_key)) {
            if (StringUtils.hasText(req.getRequestURI()) && req.getRequestURI().split("/").length > 5 && appKeyList.contains(req.getRequestURI().split("/")[5])) {
                log.info("Get Header X-Secret-Key Data2 : {}", req.getRequestURI().split("/")[5]);
                return true;
            } else {
                log.warn("Invalid Appkey");
                return false;
            }
        }
        return false;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);

            log.info("JWT validateToken : {}", claims.getBody().getSubject());
            SecurityContext securityContext = new SecurityContextImpl();
            securityContext.setAuthentication(this.getAuthentication(token));
            SecurityContextHolder.setContext(securityContext);

            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.error("잘못된 JWT 서명입니다. {}", token);
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.error("지원하지 않는 JWT 토큰입니다. {}", token);
        } catch (IllegalArgumentException e) {
            log.error("JWT 토큰이 잘못되었습니다. {}", token);
        }
        return false;
    }

    public void checkValidateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, IllegalArgumentException {
        Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
    }

    public Claims parseClaims(String accessToken) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(accessToken).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public long getRefreshValidityInMilliseconds() {
        return this.refreshValidityInMilliseconds;
    }
}
