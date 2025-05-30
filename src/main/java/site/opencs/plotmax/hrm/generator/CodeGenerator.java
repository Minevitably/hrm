package site.opencs.plotmax.hrm.generator;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.Collections;

public class CodeGenerator {
    public static void main(String[] args) {
        FastAutoGenerator.create("jdbc:mysql://plotmax.opencs.site:3306/hrm_db?useSSL=false&serverTimezone=UTC", "root", "123456")
                .globalConfig(builder -> {
                    builder.author("plotmax") // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir("D://code//java"); // 指定输出目录
                })
                .packageConfig(builder -> {
                    builder.parent("site.opencs.plotmax.hrm") // 设置父包名
                            .pathInfo(Collections.singletonMap(OutputFile.xml, "D://code//java")); // 设置mapperXml生成路径
                })
                .strategyConfig(builder -> {
                    builder.addInclude() // 留空代表所有表
                            .addTablePrefix("sys_") // 设置过滤表前缀
                            .entityBuilder() // 实体类配置
                            .enableLombok() // 启用Lombok
                            .controllerBuilder() // 控制器配置
                            .enableRestStyle(); // 启用RestController
                })
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                .execute();
    }
}
