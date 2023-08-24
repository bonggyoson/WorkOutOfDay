package wods.crossfit.global.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Workout Of Day API 명세서",
                description = "Workout of Day Community 서비스 API 명세서",
                version = "v1",
                contact = @Contact(
                        name = "bonggyoson",
                        email = "boggyoson@gmail.com"
                )))
@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi wodsOpenApi() {
        String[] paths = {"/v3/**"};

        return GroupedOpenApi.builder()
                .group("")
                .pathsToMatch(paths)
                .build();
    }
}
