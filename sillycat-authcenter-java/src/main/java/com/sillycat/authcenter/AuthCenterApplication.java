package com.sillycat.authcenter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import lombok.extern.slf4j.Slf4j;

@OpenAPIDefinition(
		servers = { 
				@Server(url = "/authcenter", description = "Default Server URL") })
@SpringBootApplication
@Slf4j
public class AuthCenterApplication {

	public static void main(String[] args) {
		log.info("SpringBootOpenAPIApplication init! ");
		SpringApplication.run(AuthCenterApplication.class, args);
		log.info("SpringBootOpenAPIApplication started! ");

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/v1/api-docs").allowedOrigins("*");
				registry.addMapping("/users/*").allowedOrigins("*").allowedMethods("GET", "POST", "PUT", "OPTIONS");
			}
		};
	}

}
