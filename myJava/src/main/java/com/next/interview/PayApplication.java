package com.next.interview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.PropertySource;

import java.util.HashMap;
import java.util.Map;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class, DataSourceTransactionManagerAutoConfiguration.class })
//@PropertySource("classpath:/Users/lixiaofeng/myGitProjects/myJava/myJava/src/main/resources/application.yml")
public class PayApplication {
    public static void main(String[] args) {

//        // 创建一个包含配置属性的 Map
//        Map<String, Object> defaultProperties = new HashMap<>();
//        defaultProperties.put("spring.config.location", "classpath:/Users/lixiaofeng/myGitProjects/myJava/myJava/src/main/resources/application.yml");
//        // 设置系统属性，指定配置文件位置
//        System.setProperty("spring.config.location", "classpath:/Users/lixiaofeng/myGitProjects/myJava/myJava/src/main/resources/application.yml");
//
//        // 创建 SpringApplication 实例
//        SpringApplication application = new SpringApplication(PayApplication.class);
//
//        // 设置默认配置属性
//        application.setDefaultProperties(defaultProperties);
//
//        // 启动应用程序
//        application.run(args);

        SpringApplication.run(PayApplication.class, args);
    }
}
