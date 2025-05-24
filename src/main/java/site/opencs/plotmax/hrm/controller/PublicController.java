package site.opencs.plotmax.hrm.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/public")
public class PublicController {
    @GetMapping("/point")
    public String publicEndpoint() {
        return "/api/public/point 这是一个公开的端点，任何人都可以访问";
    }

}
