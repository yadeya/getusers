package com.testmethod.config.doc;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Denis Yakovenko
 */
@javax.annotation.Generated(value = "io.swagger.codegen.v3.generators.java.SpringCodegen", date = "2024-02-02T17:21:08.572357999+02:00[EET]")
@Configuration
public class SwaggerDocumentationConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info()
                        .title("Simple User API")
                        .description("This is a simple API")
                        .termsOfService("")
                        .version("1.0.0")
                        .license(new License()
                                .name("")
                                .url("http://unlicense.org"))
                        .contact(new io.swagger.v3.oas.models.info.Contact()
                                .email("you@your-company.com")));
    }

}
