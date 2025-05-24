package site.opencs.plotmax.hrm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import site.opencs.plotmax.hrm.exception.ErrorCode;

import java.time.Instant;

@Data
@Builder
@Schema(description = "统一API响应格式")
public class ApiResponse<T> {
    @Schema(description = "是否成功", example = "true")
    private boolean success;

    @Schema(description = "响应数据")
    private T data;

    @Schema(description = "错误信息")
    private ErrorResponse error;

    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
                .success(true)
                .data(data)
                .build();
    }

    public static ApiResponse<Void> success() {
        return ApiResponse.<Void>builder()
                .success(true)
                .build();
    }

    public static ApiResponse<Void> error(ErrorCode errorCode, String message) {
        return ApiResponse.<Void>builder()
                .success(false)
                .error(ErrorResponse.builder()
                        .code(errorCode.name())
                        .message(message)
                        .timestamp(Instant.now())
                        .build())
                .build();
    }
}
