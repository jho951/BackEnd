package src.global.swagger.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	public OpenAPI customOpenAPI() {
		return new OpenAPI()
			.info(new Info()
				.title("BackEnd API")
				.version("v1")
				.description("API Documentation for Spring Boot App"))
			.addSecurityItem(new SecurityRequirement().addList("bearerAuth"))
			.components(new Components()
				.addSecuritySchemes("bearerAuth",
					new SecurityScheme()
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT")
				))
			.servers(List.of(
				new Server().url("http://localhost:8080").description("Local server")
			));
	}

	@Bean
	public GroupedOpenApi publicApi() {
		return GroupedOpenApi.builder()
			.group("Public APIs")
			.pathsToMatch("/api/v1/**")
			.build();
	}

	@Bean
	public GroupedOpenApi authApi() {
		return GroupedOpenApi.builder()
			.group("Auth APIs")
			.pathsToMatch("/api/v1/auth/**")
			.build();
	}
}
