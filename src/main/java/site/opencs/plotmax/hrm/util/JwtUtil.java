package site.opencs.plotmax.hrm.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import site.opencs.plotmax.hrm.config.security.SecurityProps;
import site.opencs.plotmax.hrm.exception.AuthException;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import org.springframework.security.core.GrantedAuthority;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
    private final SecurityProps securityProps;

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("roles", userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList()));

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + securityProps.getTokenValidityMs()))
                .signWith(SignatureAlgorithm.HS512, securityProps.getSecret())
                .compact();
    }

    public String generateRefreshToken(UserDetails userDetails) {
        return Jwts.builder()
                .setSubject(userDetails.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + securityProps.getRefreshTokenValidityMs()))
                .signWith(SignatureAlgorithm.HS512, securityProps.getSecret())
                .compact();
    }

    public Claims parseToken(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(securityProps.getSecret())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            log.warn("JWT解析失败: {}", e.getMessage());
            throw new AuthException("无效的令牌", ErrorCode.INVALID_TOKEN);
        }
    }

    /**
     * 获取令牌有效期（秒）
     */
    public Long getExpirationInSeconds() {
        return securityProps.getTokenValidityMs() / 1000;
    }

    /**
     * 获取令牌有效期（毫秒）
     */
    public Long getExpirationInMillis() {
        return securityProps.getTokenValidityMs();
    }
}
