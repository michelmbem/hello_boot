package org.addy.hello_boot.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI configureOpenAPI() {
        return new OpenAPI().info(
                new Info()
                        .title("Hello Boot")
                        .description("Simple Spring Boot REST API")
                        .license(getLicense())
                        .termsOfService("https://docs.github.com/site-policy/github-terms/github-terms-of-service")
                        .version("1.0.0")
                        .contact(getContact())
        );
    }

    private License getLicense() {
        return new License()
                .name("Apache-2.0")
                .url("https://www.apache.org/licenses/LICENSE-2.0.html");
    }

    private Contact getContact() {
        return new Contact()
                .name("AddySoft")
                .email("addysoft@addy.org")
                .url("https://www.addy.org/addy-soft/");
    }
}
