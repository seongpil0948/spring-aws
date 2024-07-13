package com.sixplus.server.api.core.config.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
// import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * @ProgramName : SwaggerConfig
 * @Author : LaVega
 * @Version : 1.0
 * @Description :
 */
@Configuration
public class SwaggerConfig {

	@Bean
	@ConfigurationProperties("springdoc.servers")
	public List<Server> getSpringDocServers() {
		return new ArrayList<>();
	}

	@Bean
	public OpenAPI api() {
		List<Server> servers = getSpringDocServers();
		return new OpenAPI().info(new Info().title("Stock Service")
				.description("Stock Project")
				.version("v1.0")
				.license(new License().name("Apache 2.0").url("https://stock.peachhub.love")))
				.servers(servers);
	}
}
