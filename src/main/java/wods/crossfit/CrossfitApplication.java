package wods.crossfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class CrossfitApplication {
    public static void main(String[] args) {
        SpringApplication.run(CrossfitApplication.class, args);
    }

}
