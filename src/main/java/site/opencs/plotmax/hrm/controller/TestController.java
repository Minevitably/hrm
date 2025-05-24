package site.opencs.plotmax.hrm.controller;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
public class TestController {
    @GetMapping("/whoami")
    public String whoAmI() {
        // 获取当前认证信息
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "当前用户: " + auth.getName() +
                "<br>权限: " + auth.getAuthorities();
    }

    @GetMapping("/public")
    public String publicEndpoint() {
        return "这是一个公开的端点，任何人都可以访问";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userEndpoint() {
        return "这是一个用户端点，只有USER角色可以访问";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminEndpoint() {
        return "这是一个管理员端点，只有ADMIN角色可以访问";
    }
}
