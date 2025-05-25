package site.opencs.plotmax.hrm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Slf4j
@SpringBootApplication
public class HrmApplication {

    public static void main(String[] args) {
        log.info("info");
        log.debug("debug");
        log.warn("warn");
        log.error("error");
//        System.out.println(new BCryptPasswordEncoder().encode("123456"));
        SpringApplication.run(HrmApplication.class, args);
    }

}
