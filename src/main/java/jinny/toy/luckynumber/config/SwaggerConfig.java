package jinny.toy.luckynumber.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("lottery-v1")
                .pathsToMatch("/jinny-lotto/**")
                .build();
    }

    @Bean
    public OpenAPI askFedorAPI() {
        return new OpenAPI()
                .info(new Info().title("Lucky Number API")
                                .description("Lucky Number Api.")
                                .version("v1")
//                        .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                )
                .externalDocs(new ExternalDocumentation()
                        .description("API Document"));
    }
}
