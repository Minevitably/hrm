package site.opencs.plotmax.hrm.exception;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.util.SecurityUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 认证失败（如用户名/密码错误、自定义异常）
    @ExceptionHandler(AuthException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleAuthException(AuthException ex) {
        log.warn("认证失败: {}", ex.getMessage());
        return ApiResponse.error(ex.getErrorCode(), ex.getMessage());
    }

    // 未登录访问受保护资源
    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ApiResponse<Void> handleAuthenticationException(AuthenticationException ex) {
        log.warn("未登录访问: {}", ex.getMessage());
        return ApiResponse.error(ErrorCode.UNAUTHORIZED, "请先登录");
    }

    // 已登录但权限不足
    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ApiResponse<Void> handleAccessDeniedException(AccessDeniedException ex) {
        log.warn("权限不足: {}", ex.getMessage());
        return ApiResponse.error(ErrorCode.ACCESS_DENIED, "权限不足，无法访问该资源");
    }

    // 兜底系统异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ApiResponse<Void> handleGenericException(Exception ex) {
        log.error("系统异常: {}", ex.getMessage(), ex);
        return ApiResponse.error(ErrorCode.SYSTEM_ERROR, "系统内部异常，请联系管理员");
    }

    @ExceptionHandler(BusinessException.class)
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        return ApiResponse.error(ErrorCode.SYSTEM_ERROR, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining("; "));
        return ApiResponse.error(ErrorCode.SYSTEM_ERROR, message);
    }
}
