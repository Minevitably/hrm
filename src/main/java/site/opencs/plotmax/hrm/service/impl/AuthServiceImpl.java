package site.opencs.plotmax.hrm.service.impl;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import site.opencs.plotmax.hrm.config.security.SecurityProps;
import site.opencs.plotmax.hrm.dto.request.LoginRequest;
import site.opencs.plotmax.hrm.dto.request.RefreshTokenRequest;
import site.opencs.plotmax.hrm.dto.response.AuthResponse;
import site.opencs.plotmax.hrm.exception.AuthException;
import site.opencs.plotmax.hrm.exception.ErrorCode;
import site.opencs.plotmax.hrm.service.IAuthService;
import site.opencs.plotmax.hrm.util.JwtUtil;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements IAuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private final SecurityProps securityProps;
    @Override
    public AuthResponse authenticate(LoginRequest request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String accessToken = jwtUtil.generateToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);


            return AuthResponse.builder()
                    .accessToken(accessToken)
                    .refreshToken(refreshToken)
                    .expiresIn(jwtUtil.getExpirationInSeconds())
                    .tokenType("Bearer")
                    .build();

        } catch (Exception e) {
            throw new AuthException("用户名或密码错误", ErrorCode.AUTH_FAILED);
        }
    }

    @Override
    public AuthResponse refreshToken(String refreshToken) {
        try {
            Claims claims = jwtUtil.parseToken(refreshToken);
            String username = claims.getSubject();
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            String newAccessToken = jwtUtil.generateToken(userDetails);
            String newRefreshToken = jwtUtil.generateRefreshToken(userDetails);

            return AuthResponse.builder()
                    .accessToken(newAccessToken)
                    .refreshToken(newRefreshToken)
                    .expiresIn(jwtUtil.getExpirationInSeconds())
                    .tokenType("Bearer")
                    .build();

        } catch (Exception e) {
            throw new AuthException("刷新令牌无效", ErrorCode.INVALID_TOKEN);
        }
    }

    @Override
    public void logout(String token) {
        // 实际项目中可以在这里实现令牌黑名单逻辑
        // 例如将令牌存入Redis并设置过期时间
        SecurityContextHolder.clearContext();
    }
}
