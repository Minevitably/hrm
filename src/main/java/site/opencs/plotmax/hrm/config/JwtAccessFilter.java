package site.opencs.plotmax.hrm.config;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import site.opencs.plotmax.hrm.util.JwtUtil;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

public class JwtAccessFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        try {
            String token = extractToken(request);

            if (token != null) {
                String username = JwtUtil.parseToken(token);
                if (username != null) {
                    setAuthentication(username);
                }
            }

            chain.doFilter(request, response);
        } catch (Exception e) {
            // 处理JWT解析等异常
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid token");
        }
    }

    private String extractToken(HttpServletRequest request) {
        String header = request.getHeader("Authorization");
        if (header != null && header.startsWith("Bearer ")) {
            return header.substring(7);
        }
        return null;
    }

    private void setAuthentication(String username) {
        Authentication auth = createAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    private Authentication createAuthentication(String username) {
        List<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER") // 实际应从数据库查询
        );
        return new UsernamePasswordAuthenticationToken(
                username, null, authorities);
    }
}
