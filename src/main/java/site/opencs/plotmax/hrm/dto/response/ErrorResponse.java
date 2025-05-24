package site.opencs.plotmax.hrm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.opencs.plotmax.hrm.exception.ErrorCode;

import java.time.Instant;

@Data
@Builder
@Schema(description = "错误响应格式")
public class ErrorResponse {
    @Schema(description = "错误码", example = "AUTH_FAILED")
    private String code;

    @Schema(description = "错误信息", example = "认证失败")
    private String message;

    @Schema(description = "时间戳", example = "2023-05-24T10:30:00Z")
    private Instant timestamp;

    @Schema(description = "请求路径", example = "/api/auth/login")
    private String path;

    public static ErrorResponse of(ErrorCode errorCode, String path) {
        return ErrorResponse.builder()
                .code(errorCode.name())
                .message(errorCode.getMessage())
                .timestamp(Instant.now())
                .path(path)
                .build();
    }
}
