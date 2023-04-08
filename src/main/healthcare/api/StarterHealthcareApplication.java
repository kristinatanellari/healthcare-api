package main.healthcare.api;

import main.healthcare.api.model.Users;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableWebSecurity
public class StarterHealthcareApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterHealthcareApplication.class, args);
    }
}
