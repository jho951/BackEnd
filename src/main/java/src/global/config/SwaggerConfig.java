package src.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info().title("BackEnd").version("v1").description("API Documentation for my Spring Boot app"))
			.servers(List.of(
				new Server().url("http://localhost:8080"),
				new Server().url("/")
			));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("public")
			.pathsToMatch("/v1/**")
			.build();
	}
}
