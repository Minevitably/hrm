package site.opencs.plotmax.hrm.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("site.opencs.plotmax.hrm.system.mapper") // 修改为你的实际mapper包路径
public class MyBatisPlusConfig {
}
