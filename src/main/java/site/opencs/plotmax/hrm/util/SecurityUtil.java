package site.opencs.plotmax.hrm.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.exception.ErrorCode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;
import java.security.Principal;

@Component
public class SecurityUtil {

    private static ObjectMapper objectMapper;
    // 添加常量定义
    private static final String TOKEN_HEADER = "Authorization";
    private static final String TOKEN_PREFIX = "Bearer ";

    /**
     * 从请求头中解析JWT令牌
     */
    public static String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(TOKEN_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX)) {
            return bearerToken.substring(TOKEN_PREFIX.length());
        }
        return null;
    }

    public static void sendErrorResponse(HttpServletResponse response,
                                         ErrorCode errorCode,
                                         String message) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setStatus(errorCode.getHttpStatus().value());

        objectMapper.writeValue(response.getWriter(),
                ApiResponse.error(errorCode, message));
    }

    public static Optional<String> getCurrentUsername() {
        return Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .map(Principal::getName);
    }
}
