package wods.crossfit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@PropertySource("classpath:/application-db.yml")
@SpringBootApplication
public class CrossfitApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrossfitApplication.class, args);
    }

}
