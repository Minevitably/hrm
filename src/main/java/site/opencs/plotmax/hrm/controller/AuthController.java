package site.opencs.plotmax.hrm.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import site.opencs.plotmax.hrm.dto.request.LoginRequest;
import site.opencs.plotmax.hrm.dto.request.RefreshTokenRequest;
import site.opencs.plotmax.hrm.dto.response.ApiResponse;
import site.opencs.plotmax.hrm.dto.response.AuthResponse;
import site.opencs.plotmax.hrm.service.impl.AuthServiceImpl;
import site.opencs.plotmax.hrm.util.SecurityUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
@Slf4j
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "认证管理", description = "用户登录、登出及令牌刷新")
public class AuthController {
    private final AuthServiceImpl authService;

    @PostMapping("/login")
    @Operation(summary = "用户登录")
    public ApiResponse<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        log.debug("/api/auth/login LoginRequest:");
        log.debug(request.toString());
        return ApiResponse.success(authService.authenticate(request));
    }

    @PostMapping("/refresh")
    @Operation(summary = "刷新访问令牌")
    public ApiResponse<AuthResponse> refreshToken(@Valid @RequestBody RefreshTokenRequest request) {
        return ApiResponse.success(authService.refreshToken(request.getRefreshToken()));
    }

    @PostMapping("/logout")
    @Operation(summary = "用户登出")
    public ApiResponse<Void> logout(HttpServletRequest request) {
        authService.logout(SecurityUtil.resolveToken(request));
        return ApiResponse.success();
    }
}
