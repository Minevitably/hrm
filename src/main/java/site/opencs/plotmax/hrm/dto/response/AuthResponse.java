package site.opencs.plotmax.hrm.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
@AllArgsConstructor
@Schema(description = "认证响应数据")
public class AuthResponse {
    @Schema(description = "访问令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String accessToken;

    @Schema(description = "刷新令牌", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String refreshToken;

    @Schema(description = "过期时间(秒)", example = "86400")
    private Long expiresIn;

    @Schema(description = "令牌类型", example = "Bearer")
    private String tokenType;

    // 可选：便捷构造方法
    public static AuthResponse of(String accessToken, String refreshToken) {
        return builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType("Bearer")
                .expiresIn(86400L)
                .build();
    }
}
