package me.linbo.web.user;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author linbo
 */
@MapperScan("me.linbo.web.user.mapper")
@SpringBootApplication(scanBasePackages = {"me.linbo.web", "me.linbo.web"})
public class UserServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }

}
