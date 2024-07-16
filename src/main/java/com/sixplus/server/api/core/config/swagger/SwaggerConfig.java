package com.sixplus.server.api.core.config.swagger;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
// import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

@Configuration
public class SwaggerConfig {

	@Bean
	@ConfigurationProperties("springdoc.servers")
	public List<Server> getSpringDocServers() {
		return new ArrayList<>();
	}

	@Bean
	public OpenAPI api(
			@Value("${springdoc.title:Toy-Dev-Swagger}") String title
			, @Value("${springdoc.version:v1.0.0}") String version
			, @Value("${springdoc.description:Toy-Project-Description}") String description
	) {
		String jwtSchemeName = "jwtAuth";
		SecurityRequirement securityRequirement = new SecurityRequirement().addList(jwtSchemeName);
		Info info = new Info()
				.title(title)
				.version(version)
				.description(description)
				.license(new License().name("Apache 2.0").url("https://www.idstrust.com"));


		Components components = new Components()
				.addSecuritySchemes(jwtSchemeName, new SecurityScheme()
						.name(jwtSchemeName)
						.type(SecurityScheme.Type.HTTP)
						.scheme("bearer")
						.bearerFormat("JWT"));

		List<Server> servers = getSpringDocServers();
		return new OpenAPI().info(info).servers(servers)
				.addSecurityItem(securityRequirement)
				.components(components);
	}
}
