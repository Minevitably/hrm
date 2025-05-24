package site.opencs.plotmax.hrm.service;

import site.opencs.plotmax.hrm.dto.request.LoginRequest;
import site.opencs.plotmax.hrm.dto.request.RefreshTokenRequest;
import site.opencs.plotmax.hrm.dto.response.AuthResponse;



public interface IAuthService {
    AuthResponse authenticate(LoginRequest request);
    AuthResponse refreshToken(String refreshToken);
    void logout(String token);
}
