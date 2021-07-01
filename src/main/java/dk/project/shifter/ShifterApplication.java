package dk.project.shifter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;

@SpringBootApplication(exclude = ErrorMvcAutoConfiguration.class)
public class ShifterApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShifterApplication.class, args);
    }

}
