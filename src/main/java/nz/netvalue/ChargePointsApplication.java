package nz.netvalue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebMvc
@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ChargePointsApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChargePointsApplication.class, args);
    }
}
