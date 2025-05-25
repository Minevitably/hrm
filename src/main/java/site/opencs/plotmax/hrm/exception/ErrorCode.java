package site.opencs.plotmax.hrm.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    // 认证相关
    AUTH_FAILED("认证失败", HttpStatus.UNAUTHORIZED),
    UNAUTHORIZED("未认证", HttpStatus.UNAUTHORIZED),
    INVALID_TOKEN("无效的令牌", HttpStatus.FORBIDDEN),
    EXPIRED_TOKEN("令牌已过期", HttpStatus.FORBIDDEN),
    ACCESS_DENIED("无权限访问", HttpStatus.FORBIDDEN),

    // 系统相关
    SYSTEM_ERROR("系统错误", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus httpStatus;

    ErrorCode(String message, HttpStatus httpStatus) {
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
