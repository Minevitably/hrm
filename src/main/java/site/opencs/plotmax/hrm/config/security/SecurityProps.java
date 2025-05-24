package site.opencs.plotmax.hrm.config.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@ConfigurationProperties(prefix = "app.security.jwt")
@Component // 确保被Spring管理
@Getter
@Setter
public class SecurityProps {
    private String secret;
    private long tokenValidityMs;
    private long refreshTokenValidityMs;
    private List<String> permitAllEndpoints;

    @PostConstruct
    public void printPermitEndpoints() {
        System.out.println("🚀 permitAllEndpoints: " + permitAllEndpoints);
    }


    // 可选：添加参数校验
    public void setSecret(String secret) {
        if (secret == null || secret.length() < 16) {
            throw new IllegalArgumentException("JWT secret must be at least 16 characters");
        }
        this.secret = secret;
    }
}
